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
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;

import org.jboss.logging.Logger;

import ru.iskandar.holiday.calculator.service.ejb.jms.MessageSenderBean;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelFactory;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;
import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.InvalidStatementException;
import ru.iskandar.holiday.calculator.service.model.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.StatementConsideredEvent;
import ru.iskandar.holiday.calculator.service.model.StatementEntry;
import ru.iskandar.holiday.calculator.service.model.StatementNotFoundException;
import ru.iskandar.holiday.calculator.service.model.StatementSendedEvent;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementValidator;
import ru.iskandar.holiday.calculator.service.model.permissions.Permissions;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
@DeclareRoles({ Permissions.CONSIDER })
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelLoadException {
		HolidayCalculatorModel model = new HolidayCalculatorModelFactory(_userService).create();
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	@RolesAllowed(Permissions.CONSIDER)
	@Override
	public Collection<Statement> loadStatements(EnumSet<StatementStatus> aStatuses) {
		Objects.requireNonNull(aStatuses);
		Collection<Statement> statementsByStatus = _statementRepo.getStatementsByStatus(aStatuses);
		return statementsByStatus;
	}

	/**
	 * {@inheritDoc}
	 */
	@RolesAllowed(Permissions.CONSIDER)
	@Override
	public Statement approve(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement statement = _statementRepo.getStatement(aStatement.getId(), aStatement.getStatementType());
		if (statement == null) {
			throw new StatementNotFoundException(String.format("Заявление '%s' не найдено", aStatement));
		}

		if (!StatementStatus.NOT_CONSIDERED.equals(statement.getStatus())) {
			throw new StatementAlreadyConsideredException(statement);
		}
		statement.setStatus(StatementStatus.APPROVE);
		statement.setConsider(_userService.getCurrentUser());
		statement.setConsiderDate(new Date());
		try {
			_messageSender.send(new StatementConsideredEvent(statement));
		} catch (JMSException e) {
			LOG.error(String.format("Ошибка оповещения о рассмотрении заявления %s на отгул", statement), e);
		}
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@RolesAllowed(Permissions.CONSIDER)
	@Override
	public Statement reject(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement statement = _statementRepo.getStatement(aStatement.getId(), aStatement.getStatementType());
		if (statement == null) {
			throw new StatementNotFoundException(String.format("Заявление [%s] не найдено", aStatement));
		}
		if (!StatementStatus.NOT_CONSIDERED.equals(statement.getStatus())) {
			throw new StatementAlreadyConsideredException(statement);
		}
		statement.setStatus(StatementStatus.REJECTED);
		statement.setConsider(_userService.getCurrentUser());
		statement.setConsiderDate(new Date());
		try {
			_messageSender.send(new StatementConsideredEvent(statement));
		} catch (JMSException e) {
			LOG.error(String.format("Ошибка оповещения о рассмотрении заявления %s на отгул", statement), e);
		}
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayStatement createHolidayStatement(HolidayStatementEntry aStatement)
			throws StatementAlreadySendedException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		if (StatementStatus.APPROVE.equals(aStatement.getStatus())
				|| StatementStatus.REJECTED.equals(aStatement.getStatus())) {
			throw new InvalidStatementException(
					String.format("Подаваемое заявление на отгул не может иметь статус %s", aStatement.getStatus()));
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
			LOG.error(String.format("Ошибка оповещения об отправки заявления %s на отгул", aStatement), e);
		}
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveStatement sendStatement(LeaveStatement aStatement) throws StatementAlreadySendedException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement sendedStatement = _statementRepo.getStatement(aStatement.getId(), aStatement.getStatementType());
		if (sendedStatement != null) {
			throw new StatementAlreadySendedException(aStatement, sendedStatement);
		}
		if (StatementStatus.APPROVE.equals(aStatement.getStatus())
				|| StatementStatus.REJECTED.equals(aStatement.getStatus())) {
			throw new InvalidStatementException(
					String.format("Подаваемое заявление на отпуск не может иметь статус %s", aStatement.getStatus()));
		}
		Set<Date> currentStatementDays = aStatement.getLeaveDates();
		for (Statement st : getCurrentUserStatements()) {
			switch (st.getStatementType()) {
			case HOLIDAY_STATEMENT:
				if (DateUtils.hasIntersectionDays(((HolidayStatement) st).getDays(), currentStatementDays)) {
					throw new StatementAlreadySendedException(aStatement, st);
				}
				break;
			case LEAVE_STATEMENT:
				if (DateUtils.hasIntersectionDays(((LeaveStatement) st).getLeaveDates(), currentStatementDays)) {
					throw new StatementAlreadySendedException(aStatement, st);
				}
				break;
			default:
				break;

			}

		}
		_statementRepo.save(aStatement);
		try {
			_messageSender.send(new StatementSendedEvent(aStatement));
		} catch (JMSException e) {
			LOG.error(String.format("Ошибка оповещения об отправки заявления %s на отпуск", aStatement), e);
		}
		return aStatement;
	}

	private Collection<HolidayStatement> getCurrentUserHolidayStatements() {
		User currentUser = _userService.getCurrentUser();
		return _statementRepo.getHolidayStatementsByAuthor(currentUser);
	}

	private Collection<RecallStatement> getCurrentUserRecallStatements() {
		User currentUser = _userService.getCurrentUser();
		return _statementRepo.getRecallStatementsByAuthor(currentUser);
	}

	private Collection<Statement> getStatementsByUser(User aUser) {
		Objects.requireNonNull(aUser);
		return _statementRepo.getStatementsByAuthor(aUser);
	}

	private Collection<Statement> getCurrentUserStatements() {
		return getStatementsByUser(_userService.getCurrentUser());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHolidaysQuantity(User aUser) {
		int recallCount = 0;
		int holidayCount = 0;
		for (Statement st : getStatementsByUser(aUser)) {
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
		for (Statement st : getStatementsByUser(aUser)) {
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
		for (Statement st : getStatementsByUser(aUser)) {
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
	@RolesAllowed(Permissions.CONSIDER)
	@Override
	public Collection<Statement> getIncomingStatements() {
		Collection<Statement> incoming = _statementRepo
				.getStatementsByStatus(EnumSet.of(StatementStatus.NOT_CONSIDERED));
		return incoming;
	}

	/**
	 * Проверяет коррекность заполнения заявления
	 *
	 * @param aStatement
	 *            заявление
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	private void checkStatement(Statement aStatement) {
		Objects.requireNonNull(aStatement);
		_statementValidator.checkStatement(aStatement);
	}

	/**
	 * Проверяет коррекность заполнения заявления
	 *
	 * @param aStatement
	 *            заявление
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	private void checkStatement(StatementEntry aStatement) {
		Objects.requireNonNull(aStatement);
		_statementValidator.checkStatement(aStatement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConsider() {
		boolean canConsider = _permissionsService.hasPermission(PermissionId.from(Permissions.CONSIDER));
		return canConsider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLeaveCount(User aUser) {
		int leaveCount = 0;
		for (Statement st : getStatementsByUser(aUser)) {
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
		for (Statement st : getStatementsByUser(aUser)) {
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
	public RecallStatement sendStatement(RecallStatement aStatement) throws StatementAlreadySendedException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement sendedStatement = _statementRepo.getStatement(aStatement.getId(), aStatement.getStatementType());
		if (sendedStatement != null) {
			throw new StatementAlreadySendedException(aStatement, sendedStatement);
		}
		if (StatementStatus.APPROVE.equals(aStatement.getStatus())
				|| StatementStatus.REJECTED.equals(aStatement.getStatus())) {
			throw new InvalidStatementException(
					String.format("Подаваемое заявление на отзыв не может иметь статус %s", aStatement.getStatus()));
		}
		Set<Date> currentStatementDays = aStatement.getRecallDates();
		for (RecallStatement st : getCurrentUserRecallStatements()) {
			if (DateUtils.hasIntersectionDays(st.getRecallDates(), currentStatementDays)) {
				throw new StatementAlreadySendedException(aStatement, st);
			}
		}
		_statementRepo.save(aStatement);
		try {
			_messageSender.send(new StatementSendedEvent(aStatement));
		} catch (JMSException e) {
			LOG.error(String.format("Ошибка оповещения об отправки заявления %s на отзыв", aStatement), e);
		}
		return aStatement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Statement> getAllStatements(User aUser) {
		Objects.requireNonNull(aUser);
		return getStatementsByUser(aUser);
	}

}