package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.Statement;

/**
 * Исключение для случая, когда заявление уже было рассмотрено
 */
public class StatementAlreadyConsideredException extends HolidayCalculatorException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 6538235553747411882L;

	/** заявление */
	private final Statement _statement;

	/**
	 * @param aMessage
	 */
	public StatementAlreadyConsideredException(Statement aStatement) {
		super(String.format("Заявление %s уже было отправлено ранее", aStatement));
		_statement = aStatement;
	}

	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return _statement;
	}

}
