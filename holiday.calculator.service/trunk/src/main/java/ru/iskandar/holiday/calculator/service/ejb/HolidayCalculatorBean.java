package ru.iskandar.holiday.calculator.service.ejb;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;

import ru.iskandar.holiday.calculator.service.ejb.jms.MessageSenderBean;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelFactory;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;
import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.HolidayStatementSendedEvent;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {

	/** Сервис работы с текущим пользователем */
	@EJB
	private IUserServiceLocal _currentUserServiceLocal;

	/** Отправитель сообщения */
	@EJB
	private MessageSenderBean _messageSender;
	// TODO имитация БД
	private static Set<Statement> _statements = new HashSet<>();

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
		for (Statement statement : _statements) {
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
		aStatement.setStatus(StatementStatus.APPROVE);
		_statements.remove(aStatement);
		_statements.add(aStatement);
		return aStatement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement reject(Statement aStatement) {
		aStatement.setStatus(StatementStatus.REJECTED);
		_statements.remove(aStatement);
		_statements.add(aStatement);
		return aStatement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayStatement sendStatement(HolidayStatement aStatement) throws HolidayCalculatorServiceException {
		if (_statements.contains(aStatement)) {
			throw new IllegalStateException(String.format("Заявление %s уже подано", aStatement));
		}
		_statements.add(aStatement);
		try {
			_messageSender.send(new HolidayStatementSendedEvent(aStatement));
		} catch (JMSException e) {
			throw new HolidayCalculatorServiceException("Ошибка оповещения об отправки заявления на отгул", e);
		}
		return aStatement;
	}

}