package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
import ru.iskandar.holiday.calculator.service.model.HolidayStatementSendedEvent;
import ru.iskandar.holiday.calculator.service.model.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementConsideredEvent;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementType;
import ru.iskandar.holiday.calculator.service.model.User;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {

	/** Логгер */
	private static final Logger LOG = Logger.getLogger(HolidayCalculatorBean.class);

	/** Сервис работы пользователеми */
	@EJB
	private IUserServiceLocal _userService;

	/** Отправитель сообщения */
	@EJB
	private MessageSenderBean _messageSender;

	// TODO имитация БД
	private static Map<UUID, Statement> _statements = new HashMap<>();

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
	// @RolesAllowed (Permissions.CONSIDER.getId())
	@Override
	public Set<Statement> loadStatements(EnumSet<StatementStatus> aStatuses) {
		Objects.requireNonNull(aStatuses);
		Set<Statement> statementsByStatus = new HashSet<>();
		for (Statement statement : _statements.values()) {
			if (aStatuses.contains(statement.getStatus())) {
				statementsByStatus.add(statement);
			}
		}
		return statementsByStatus;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement approve(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement statement = _statements.get(aStatement.getUuid());
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
	@Override
	public Statement reject(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement statement = _statements.get(aStatement.getUuid());
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
	public HolidayStatement sendStatement(HolidayStatement aStatement) throws StatementAlreadySendedException {
		Objects.requireNonNull(aStatement);
		checkStatement(aStatement);

		Statement sendedStatement = _statements.get(aStatement.getUuid());
		if (sendedStatement != null) {
			throw new StatementAlreadySendedException(aStatement, sendedStatement);
		}
		if (StatementStatus.APPROVE.equals(aStatement.getStatus())
				|| StatementStatus.REJECTED.equals(aStatement.getStatus())) {
			throw new InvalidStatementException(
					String.format("Подаваемое заявление не может иметь статус %s", aStatement.getStatus()));
		}
		Set<Date> currentStatementDays = aStatement.getDays();
		for (HolidayStatement st : getCurrentUserHolidayStatements()) {
			if (DateUtils.hasIntersectionDays(st.getDays(), currentStatementDays)) {
				throw new StatementAlreadySendedException(aStatement, st);
			}
		}
		_statements.put(aStatement.getUuid(), aStatement);
		try {
			_messageSender.send(new HolidayStatementSendedEvent(aStatement));
		} catch (JMSException e) {
			LOG.error(String.format("Ошибка оповещения об отправки заявления %s на отгул", aStatement), e);
		}
		return aStatement;
	}

	private Set<HolidayStatement> getCurrentUserHolidayStatements() {
		User currentUser = _userService.getCurrentUser();
		Set<HolidayStatement> currentUserStatements = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(currentUser) && (st instanceof HolidayStatement)) {
				currentUserStatements.add((HolidayStatement) st);
			}
		}
		return currentUserStatements;
	}

	private Set<Statement> getStatementsByUser(User aUser) {
		Objects.requireNonNull(aUser);
		Set<Statement> statementsByUser = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(aUser)) {
				statementsByUser.add(st);
			}
		}
		return statementsByUser;
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
	@Override
	public Collection<Statement> getIncomingStatements() {
		Collection<Statement> incoming = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (StatementStatus.NOT_CONSIDERED.equals(st.getStatus())) {
				incoming.add(st);
			}
		}
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
		if (aStatement.getAuthor() == null) {
			throw new InvalidStatementException("Не указан автор заявления");
		}
		StatementType type = aStatement.getStatementType();
		if (type == null) {
			throw new InvalidStatementException("Не указан тип заявления");
		}
		if (aStatement.getUuid() == null) {
			throw new InvalidStatementException("Не указан UUID заявления");
		}
		if (aStatement.getCreateDate() == null) {
			throw new InvalidStatementException("Не указана дата создания заявления");
		}
		StatementStatus status = aStatement.getStatus();
		if (status == null) {
			throw new InvalidStatementException("Не указан статус заявления");
		}

		switch (status) {
		case APPROVE:
		case REJECTED:
			if (aStatement.getConsider() == null) {
				throw new InvalidStatementException("Не указан пользователь, рассмотревший заявление");
			}
			if (aStatement.getConsiderDate() == null) {
				throw new InvalidStatementException("Не указана дата рассмотрения заявления");
			}
			break;

		default:
			break;
		}

		switch (type) {
		case HOLIDAY_STATEMENT:
			HolidayStatement holidayStatement = (HolidayStatement) aStatement;
			if ((holidayStatement.getDays() == null) || holidayStatement.getDays().isEmpty()) {
				throw new InvalidStatementException("Не указаны дни (день) на отгул");
			}
			break;

		case RECALL_STATEMENT:
			RecallStatement recallStatement = (RecallStatement) aStatement;
			if ((recallStatement.getRecallDates() == null) || recallStatement.getRecallDates().isEmpty()) {
				throw new InvalidStatementException("Не указаны дни (день) на отзыв");
			}
			break;

		default:
			break;
		}
	}

}