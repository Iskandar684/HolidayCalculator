package ru.iskandar.holiday.calculator.service.model;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Исключение для случая, когда заявление уже было рассмотрено
 */
public class StatementAlreadyConsideredException extends HolidayCalculatorException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 6538235553747411882L;

	/** заявление */
	private final Statement<?> _statement;

	/**
	 * @param aMessage
	 */
	public StatementAlreadyConsideredException(Statement<?> aStatement) {
		super(createMess(aStatement));
		_statement = aStatement;
	}

	private static String createMess(Statement<?> aStatement) {
		String considerDate = DateUtils.format(aStatement.getConsiderDate(), DateUtils.DATE_TIME_FORMAT);
		return String.format("%s уже было рассмотрено ранее %s %s.", aStatement.getDescription(),
				aStatement.getConsider().getFIO(), considerDate);
	}

	/**
	 * @return the statement
	 */
	public Statement<?> getStatement() {
		return _statement;
	}

}
