package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.Statement;

/**
 * Исключение для случая, когда заявление уже было отправлено
 */
public class StatementAlreadySendedException extends HolidayCalculatorException {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = -2540748294773759275L;

	/** Отправляемое заявление */
	private final Statement _sendingStatement;

	/** Ранее отправленное заявление */
	private final Statement _earlySendenStatement;

	/**
	 * @param aMessage
	 */
	public StatementAlreadySendedException(Statement aSendingStatement, Statement aEarlySendenStatement) {
		super(String.format("Заявление %s уже было отправлено ранее", aSendingStatement));
		_sendingStatement = aSendingStatement;
		_earlySendenStatement = aEarlySendenStatement;
	}

	/**
	 * @return the sendingStatement
	 */
	public Statement getSendingStatement() {
		return _sendingStatement;
	}

	/**
	 * @return the earlySendenStatement
	 */
	public Statement getEarlySendenStatement() {
		return _earlySendenStatement;
	}

}
