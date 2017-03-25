package ru.iskandar.holiday.calculator.service.ejb;

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
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
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

	/** Сервис работы с текущим пользователем */
	@EJB
	private IUserServiceLocal _currentUserServiceLocal;

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
		HolidayCalculatorModel model = new HolidayCalculatorModelFactory(_currentUserServiceLocal).create();
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
	public Statement approve(Statement aStatement) {
		Statement statement = _statements.get(aStatement.getUuid());
		statement.setStatus(StatementStatus.APPROVE);
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement reject(Statement aStatement) {
		Statement statement = _statements.get(aStatement.getUuid());
		statement.setStatus(StatementStatus.REJECTED);
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayStatement sendStatement(HolidayStatement aStatement) throws StatementAlreadySendedException {
		Objects.requireNonNull(aStatement);

		Statement sendedStatement = _statements.get(aStatement.getUuid());
		if (sendedStatement != null) {
			throw new StatementAlreadySendedException(aStatement, sendedStatement);
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
		User currentUser = _currentUserServiceLocal.getCurrentUser();
		Set<HolidayStatement> currentUserStatements = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(currentUser) && (st instanceof HolidayStatement)) {
				currentUserStatements.add((HolidayStatement) st);
			}
		}
		return currentUserStatements;
	}

}