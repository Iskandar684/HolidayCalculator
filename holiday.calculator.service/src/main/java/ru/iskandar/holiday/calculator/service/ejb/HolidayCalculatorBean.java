package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;

import org.jboss.logging.Logger;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.service.ejb.jms.MessageSenderBean;
import ru.iskandar.holiday.calculator.service.ejb.search.ISearchServiceLocal;
import ru.iskandar.holiday.calculator.service.ejb.search.SearchServiceException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelFactory;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;
import ru.iskandar.holiday.calculator.service.model.InvalidStatementException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.StatementConsideredEvent;
import ru.iskandar.holiday.calculator.service.model.StatementNotFoundException;
import ru.iskandar.holiday.calculator.service.model.StatementSendedEvent;
import ru.iskandar.holiday.calculator.service.model.UserCreatedEvent;
import ru.iskandar.holiday.calculator.service.model.document.DocumentPreviewException;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.service.model.permissions.Permission;
import ru.iskandar.holiday.calculator.service.model.search.ISearchResult;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.statement.StatementValidator;
import ru.iskandar.holiday.calculator.service.model.user.NewUserNotValidException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginAlreadyExistException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;
import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
@Local(IHolidayCalculatorLocal.class)
@DeclareRoles({Permission.CONSIDER, Permission.USER_CREATOR, Permission.USER_VIEWER})
public class HolidayCalculatorBean implements IHolidayCalculatorRemote, IHolidayCalculatorLocal {

    /** Логгер */
    private static final Logger LOG = Logger.getLogger(HolidayCalculatorBean.class);

    /** Сервис работы пользователеми */
    @EJB
    private IUserServiceLocal _userService;

    /** Отправитель сообщения */
    @EJB
    private MessageSenderBean _messageSender;

    /** Сервис полномочий */
    @EJB
    private IPermissionsServiceLocal _permissionsService;

    /** Валидатор заявления */
    private final StatementValidator _statementValidator = new StatementValidator();

    /** Репозиторий заявлений */
    @EJB
    private IStatementRepository _statementRepo;

    /** Сервис печатной формы заявлений */
    @EJB
    private IHolidayCalculatorReportService _reportService;

    /** Сервис поиска */
    @EJB
    private ISearchServiceLocal _searchService;

    /**
     * {@inheritDoc}
     */
    @Override
    public HolidayCalculatorModel loadHolidayCalculatorModel()
            throws HolidayCalculatorModelLoadException {
        HolidayCalculatorModel model = new HolidayCalculatorModelFactory(_userService).create();
        return model;
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(Permission.CONSIDER)
    @Override
    public Collection<Statement<?>> loadStatements(EnumSet<StatementStatus> aStatuses) {
        Objects.requireNonNull(aStatuses);
        Collection<Statement<?>> statementsByStatus =
                _statementRepo.getStatementsByStatus(aStatuses);
        return statementsByStatus;
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(Permission.CONSIDER)
    @Override
    public Statement<?> approve(Statement<?> aStatement)
            throws StatementAlreadyConsideredException {
        Objects.requireNonNull(aStatement);
        checkStatement(aStatement);

        Statement<?> statement =
                _statementRepo.getStatement(aStatement.getId(), aStatement.getStatementType());
        if (statement == null) {
            throw new StatementNotFoundException(
                    String.format("Заявление '%s' не найдено", aStatement));
        }

        if (!StatementStatus.NOT_CONSIDERED.equals(statement.getStatus())) {
            throw new StatementAlreadyConsideredException(statement);
        }
        statement.setStatus(StatementStatus.APPROVE);
        statement.setConsider(_userService.getCurrentUser());
        statement.setConsiderDate(new Date());
        _statementRepo.save(statement);
        try {
            _messageSender.send(new StatementConsideredEvent(statement));
        } catch (JMSException e) {
            LOG.error(String.format("Ошибка оповещения о рассмотрении заявления %s на отгул",
                    statement), e);
        }
        addStatementToSearchService(statement);
        return statement;
    }

    @Override
    public Statement<?> approve(StatementId aStatementID)
            throws StatementAlreadyConsideredException {
        return approve(getStatement(aStatementID));
    }

    @Override
    public Statement<?> reject(StatementId aStatementID)
            throws StatementAlreadyConsideredException {
        return reject(getStatement(aStatementID));
    }

    @RolesAllowed(Permission.CONSIDER)
    @Override
    public Statement<?> reject(Statement<?> aStatement) throws StatementAlreadyConsideredException {
        Objects.requireNonNull(aStatement);
        checkStatement(aStatement);

        Statement<?> statement =
                _statementRepo.getStatement(aStatement.getId(), aStatement.getStatementType());
        if (statement == null) {
            throw new StatementNotFoundException(
                    String.format("Заявление [%s] не найдено", aStatement));
        }
        if (!StatementStatus.NOT_CONSIDERED.equals(statement.getStatus())) {
            throw new StatementAlreadyConsideredException(statement);
        }
        statement.setStatus(StatementStatus.REJECTED);
        statement.setConsider(_userService.getCurrentUser());
        statement.setConsiderDate(new Date());
        _statementRepo.save(statement);
        try {
            _messageSender.send(new StatementConsideredEvent(statement));
        } catch (JMSException e) {
            LOG.error(String.format("Ошибка оповещения о рассмотрении заявления %s на отгул",
                    statement), e);
        }
        addStatementToSearchService(statement);
        return statement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HolidayStatement createHolidayStatement(HolidayStatementEntry aStatement)
            throws StatementAlreadySendedException {
        Objects.requireNonNull(aStatement);
        checkStatementEntry(aStatement);

        if (StatementStatus.APPROVE.equals(aStatement.getStatus())
                || StatementStatus.REJECTED.equals(aStatement.getStatus())) {
            throw new InvalidStatementException(
                    String.format("Подаваемое заявление на отгул не может иметь статус %s",
                            aStatement.getStatus()));
        }
        Set<Date> currentStatementDays = aStatement.getDays();
        for (HolidayStatement st : getCurrentUserHolidayStatements()) {
            if (DateUtils.hasIntersectionDays(st.getDays(), currentStatementDays)) {
                throw new StatementAlreadySendedException(aStatement, st);
            }
        }

        HolidayStatement statement = _statementRepo.createHolidayStatement(aStatement);
        try {
            _messageSender.send(new StatementSendedEvent(statement));
        } catch (JMSException e) {
            LOG.error(String.format("Ошибка оповещения об отправки заявления %s на отгул",
                    aStatement), e);
        }
        addStatementToSearchService(statement);
        return statement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LeaveStatement createLeaveStatement(LeaveStatementEntry aEntry)
            throws StatementAlreadySendedException {
        Objects.requireNonNull(aEntry);
        checkStatementEntry(aEntry);

        if (StatementStatus.APPROVE.equals(aEntry.getStatus())
                || StatementStatus.REJECTED.equals(aEntry.getStatus())) {
            throw new InvalidStatementException(String.format(
                    "Подаваемое заявление на отпуск не может иметь статус %s", aEntry.getStatus()));
        }
        Set<Date> currentStatementDays = aEntry.getLeaveDates();
        for (Statement<?> st : getCurrentUserStatements()) {
            switch (st.getStatementType()) {
            case HOLIDAY_STATEMENT:
                if (DateUtils.hasIntersectionDays(((HolidayStatement) st).getDays(),
                        currentStatementDays)) {
                    throw new StatementAlreadySendedException(aEntry, st);
                }
                break;
            case LEAVE_STATEMENT:
                if (DateUtils.hasIntersectionDays(((LeaveStatement) st).getLeaveDates(),
                        currentStatementDays)) {
                    throw new StatementAlreadySendedException(aEntry, st);
                }
                break;
            default:
                break;

            }

        }
        LeaveStatement st = _statementRepo.createLeaveStatement(aEntry);
        try {
            _messageSender.send(new StatementSendedEvent(st));
        } catch (JMSException e) {
            LOG.error(String.format("Ошибка оповещения об отправки заявления %s на отпуск", st), e);
        }
        addStatementToSearchService(st);
        return st;
    }

    private Collection<HolidayStatement> getCurrentUserHolidayStatements() {
        User currentUser = _userService.getCurrentUser();
        return _statementRepo.getHolidayStatementsByAuthor(currentUser);
    }

    private Collection<RecallStatement> getCurrentUserRecallStatements() {
        User currentUser = _userService.getCurrentUser();
        return _statementRepo.getRecallStatementsByAuthor(currentUser);
    }

    private Collection<Statement<?>> getStatementsByUser(User aUser) {
        Objects.requireNonNull(aUser);
        return _statementRepo.getStatementsByAuthor(aUser);
    }

    private Collection<Statement<?>> getCurrentUserStatements() {
        return getStatementsByUser(_userService.getCurrentUser());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHolidaysQuantity(User aUser) {
        int recallCount = 0;
        int holidayCount = 0;
        for (Statement<?> st : getStatementsByUser(aUser)) {
            if (st instanceof HolidayStatement) {
                HolidayStatement holidaySt = (HolidayStatement) st;
                if (StatementStatus.APPROVE.equals(holidaySt.getStatus())) {
                    holidayCount += holidaySt.getDays().size();
                }
            } else if (st instanceof RecallStatement) {
                RecallStatement recallStatement = (RecallStatement) st;
                if (StatementStatus.APPROVE.equals(recallStatement.getStatus())) {
                    recallCount += ((RecallStatement) st).getRecallDates().size();
                }
            }
        }
        int holidaysQuantity = recallCount - holidayCount;
        return holidaysQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOutgoingHolidaysQuantity(User aUser) {
        int outgoingHolidayCount = 0;
        for (Statement<?> st : getStatementsByUser(aUser)) {
            if (st instanceof HolidayStatement) {
                HolidayStatement holidaySt = (HolidayStatement) st;
                if (StatementStatus.NOT_CONSIDERED.equals(holidaySt.getStatus())) {
                    outgoingHolidayCount += holidaySt.getDays().size();
                }
            }
        }
        return outgoingHolidayCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIncomingHolidaysQuantity(User aUser) {
        int incomingHolidays = 0;
        for (Statement<?> st : getStatementsByUser(aUser)) {
            if (st instanceof RecallStatement) {
                RecallStatement recallStatement = (RecallStatement) st;
                if (StatementStatus.NOT_CONSIDERED.equals(recallStatement.getStatus())) {
                    incomingHolidays += ((RecallStatement) st).getRecallDates().size();
                }
            }
        }
        return incomingHolidays;
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(Permission.CONSIDER)
    @Override
    public Collection<Statement<?>> getIncomingStatements() {
        Collection<Statement<?>> incoming =
                _statementRepo.getStatementsByStatus(EnumSet.of(StatementStatus.NOT_CONSIDERED));
        return incoming;
    }

    /**
     * Проверяет коррекность заполнения заявления
     *
     * @param aStatement заявление
     * @throws InvalidStatementException если заявление заполнено некорректно
     */
    private void checkStatement(Statement<?> aStatement) {
        Objects.requireNonNull(aStatement);
        _statementValidator.checkStatement(aStatement);
    }

    /**
     * Проверяет коррекность заполнения заявления
     *
     * @param aStatement заявление
     * @throws InvalidStatementException если заявление заполнено некорректно
     */
    private void checkStatementEntry(StatementEntry aStatement) {
        Objects.requireNonNull(aStatement);
        _statementValidator.checkStatement(aStatement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConsider() {
        boolean canConsider =
                _permissionsService.hasPermission(PermissionId.from(Permission.CONSIDER));
        return canConsider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLeaveCount(User aUser) {
        int leaveCount = 0;
        for (Statement<?> st : getStatementsByUser(aUser)) {
            switch (st.getStatementType()) {
            case LEAVE_STATEMENT:
                LeaveStatement holidaySt = (LeaveStatement) st;
                if (StatementStatus.APPROVE.equals(holidaySt.getStatus())) {
                    leaveCount += holidaySt.getLeaveDates().size();
                }
                break;

            default:
                break;
            }

        }
        int remaining = 28 - leaveCount;
        return remaining;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOutgoingLeaveCount(User aUser) {
        int outgoing = 0;
        for (Statement<?> st : getStatementsByUser(aUser)) {
            switch (st.getStatementType()) {
            case LEAVE_STATEMENT:
                LeaveStatement holidaySt = (LeaveStatement) st;
                if (StatementStatus.NOT_CONSIDERED.equals(holidaySt.getStatus())) {
                    outgoing += holidaySt.getLeaveDates().size();
                }
                break;

            default:
                break;
            }

        }
        return outgoing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getNextLeaveStartDate(User aUser) {
        Date employmentDate = aUser.getEmploymentDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(employmentDate);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        return cal.getTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecallStatement createRecallStatement(RecallStatementEntry aEntry)
            throws StatementAlreadySendedException {
        Objects.requireNonNull(aEntry);
        checkStatementEntry(aEntry);

        if (StatementStatus.APPROVE.equals(aEntry.getStatus())
                || StatementStatus.REJECTED.equals(aEntry.getStatus())) {
            throw new InvalidStatementException(String.format(
                    "Подаваемое заявление на отзыв не может иметь статус %s", aEntry.getStatus()));
        }
        Set<Date> currentStatementDays = aEntry.getRecallDates();
        for (RecallStatement st : getCurrentUserRecallStatements()) {
            if (DateUtils.hasIntersectionDays(st.getRecallDates(), currentStatementDays)) {
                throw new StatementAlreadySendedException(aEntry, st);
            }
        }
        RecallStatement statement = _statementRepo.createRecallStatement(aEntry);
        try {
            _messageSender.send(new StatementSendedEvent(statement));
        } catch (JMSException e) {
            LOG.error(
                    String.format("Ошибка оповещения об отправки заявления %s на отзыв", statement),
                    e);
        }
        addStatementToSearchService(statement);
        return statement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Statement<?>> getAllStatements(User aUser) {
        Objects.requireNonNull(aUser);
        return getStatementsByUser(aUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkAuthentification() throws UserByLoginNotFoundException {
        User user = _userService.getCurrentUser();
        if (user == null) {
            throw new UserByLoginNotFoundException("Описание текущего пользователя не найдено");
        }

    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(Permission.USER_CREATOR)
    @Override
    public User createUser(NewUserEntry aNewUserEntry, Set<PermissionId> aNewUserPermissions)
            throws UserByLoginAlreadyExistException {
        Objects.requireNonNull(aNewUserEntry);
        Objects.requireNonNull(aNewUserPermissions);
        checkCreatingUser(aNewUserEntry);

        User alreadyExistUser = _userService.findUserByLogin(aNewUserEntry.getLogin());
        if (alreadyExistUser != null) {
            throw new UserByLoginAlreadyExistException(
                    String.format("Пользователь с указанным логином '%s' уже существует",
                            aNewUserEntry.getLogin()));
        }

        User newUser = _userService.createUser(aNewUserEntry, aNewUserPermissions);
        try {
            _messageSender.send(new UserCreatedEvent(newUser));
        } catch (JMSException e) {
            LOG.error(String.format("Ошибка оповещения о создании пользователя %s", newUser), e);
        }
        return newUser;
    }

    /**
     * Проверяет валидность заполнения описания создаваемого пользователя
     *
     * @param aNewUserEntry описание создаваемого пользователя
     * @throws NewUserNotValidException если описание создаваемого пользователя
     *             заполнено неверно
     */
    private void checkCreatingUser(NewUserEntry aNewUserEntry) {
        Objects.requireNonNull(aNewUserEntry);
        if ((aNewUserEntry.getLogin() == null) || aNewUserEntry.getLogin().isEmpty()) {
            throw new NewUserNotValidException(
                    String.format("Не указан логин создаваемого пользователя %s", aNewUserEntry));
        }
        if ((aNewUserEntry.getFirstName() == null) || aNewUserEntry.getFirstName().isEmpty()) {
            throw new NewUserNotValidException(
                    String.format("Не указано имя создаваемого пользователя %s", aNewUserEntry));
        }
        if ((aNewUserEntry.getLastName() == null) || aNewUserEntry.getLastName().isEmpty()) {
            throw new NewUserNotValidException(String
                    .format("Не указана фамилия создаваемого пользователя %s", aNewUserEntry));
        }
        if ((aNewUserEntry.getPatronymic() == null) || aNewUserEntry.getPatronymic().isEmpty()) {
            throw new NewUserNotValidException(String
                    .format("Не указано отчество создаваемого пользователя %s", aNewUserEntry));
        }
        if (aNewUserEntry.getEmploymentDate() == null) {
            throw new NewUserNotValidException(
                    String.format("Не указана дата приема на работу создаваемого пользователя %s",
                            aNewUserEntry));
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canCreateUser() {
        boolean canCreate =
                _permissionsService.hasPermission(PermissionId.from(Permission.USER_CREATOR));
        return canCreate;
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(Permission.USER_VIEWER)
    @Override
    public Collection<User> getAllUsers() {
        return _userService.getAllUsers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canViewUsers() {
        boolean canView =
                _permissionsService.hasPermission(PermissionId.from(Permission.USER_VIEWER));
        return canView;
    }

    @Override
    public StatementDocument preview(HolidayStatementEntry aEntry) throws DocumentPreviewException {
        Objects.requireNonNull(aEntry, "Не указано содержание заявления на отгул");
        IReport report;
        try {
            report = _reportService.generate(aEntry);
        } catch (HolidayCalculatorException e) {
            String err = String.format("Ошибка генерациии документа для заявления %s", aEntry);
            LOG.error(err, e);
            throw new DocumentPreviewException(err, e);
        }
        return new StatementDocument(report.getContent());
    }

    @Override
    public StatementDocument preview(LeaveStatementEntry aEntry) throws DocumentPreviewException {
        Objects.requireNonNull(aEntry, "Не указано содержание заявления на отпуск");
        IReport report;
        try {
            report = _reportService.generate(aEntry);
        } catch (HolidayCalculatorException e) {
            String err = String.format("Ошибка генерациии документа для заявления %s", aEntry);
            LOG.error(err, e);
            throw new DocumentPreviewException(err, e);
        }
        return new StatementDocument(report.getContent());
    }

    @Override
    public StatementDocument preview(RecallStatementEntry aEntry) throws DocumentPreviewException {
        Objects.requireNonNull(aEntry, "Не указано содержание заявления на отзыв");
        IReport report;
        try {
            report = _reportService.generate(aEntry);
        } catch (HolidayCalculatorException e) {
            String err = String.format("Ошибка генерациии документа для заявления %s", aEntry);
            LOG.error(err, e);
            throw new DocumentPreviewException(err, e);
        }
        return new StatementDocument(report.getContent());
    }

    @Override
    public StatementDocument getStatementDocument(StatementId aStatementID)
            throws DocumentPreviewException {
        HolidayStatement holidayStatement = _statementRepo.getHolidayStatement(aStatementID);
        if (holidayStatement != null) {
            // отгул
            return preview(holidayStatement.getEntry());
        }
        LeaveStatement leaveStatement = _statementRepo.getLeaveStatement(aStatementID);
        if (leaveStatement != null) {
            // отпуск
            return preview(leaveStatement.getEntry());
        }
        RecallStatement recallStatement = _statementRepo.getRecallStatement(aStatementID);
        if (recallStatement != null) {
            // отзыв
            return preview(recallStatement.getEntry());
        }
        throw new DocumentPreviewException(
                String.format("Заявление по идентификатору %s на найдено.", aStatementID));
    }

    private Statement<?> getStatement(StatementId aStatementID) {
        HolidayStatement holidayStatement = _statementRepo.getHolidayStatement(aStatementID);
        if (holidayStatement != null) {
            // отгул
            return holidayStatement;
        }
        LeaveStatement leaveStatement = _statementRepo.getLeaveStatement(aStatementID);
        if (leaveStatement != null) {
            // отпуск
            return leaveStatement;
        }
        RecallStatement recallStatement = _statementRepo.getRecallStatement(aStatementID);
        if (recallStatement != null) {
            // отзыв
            return recallStatement;
        }
        throw new StatementNotFoundException(
                String.format("Заявление [%s] не найдено", aStatementID));
    }

    @Override
    public void changeNote(UserId aUserId, String aNewNote) {
        _userService.changeNote(aUserId, aNewNote);
    }

    @Override
    public ISearchResult search(String aSearchText) throws SearchServiceException {
        return _searchService.search(aSearchText);
    }

    /**
     * Добавляет заявление в систему поиска.
     *
     * @param aStatement заявление
     */
    private void addStatementToSearchService(Statement<?> aStatement) {
        try {
            _searchService.addOrUpdate(aStatement);
        } catch (SearchServiceException e) {
            String mess =
                    String.format("Ошибка добавления заявления [%s] в Elastic Search", aStatement);
            LOG.debug(mess, e);
            LOG.warn(mess + ": " + e.getLocalizedMessage());
        }
    }

}