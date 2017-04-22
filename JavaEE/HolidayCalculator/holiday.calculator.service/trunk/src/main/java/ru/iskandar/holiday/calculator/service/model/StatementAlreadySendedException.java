package ru.iskandar.holiday.calculator.service.model;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;

/**
 * Исключение для случая, когда заявление уже было отправлено
 */
public class StatementAlreadySendedException extends HolidayCalculatorException {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = -2540748294773759275L;

	/** Отправляемое заявление */
	private final StatementEntry _sendingStatement;

	/** Ранее отправленное заявление */
	private final Statement _earlySendenStatement;

	/**
	 * @param aMessage
	 */
	public StatementAlreadySendedException(StatementEntry aSendingStatement, Statement aEarlySendenStatement) {
		super(String.format("Заявление %s уже было отправлено ранее", aSendingStatement));
		_sendingStatement = aSendingStatement;
		_earlySendenStatement = aEarlySendenStatement;
	}

	/**
	 * @param aMessage
	 * @deprecated
	 */
	@Deprecated
	public StatementAlreadySendedException(Statement aSendingStatement, Statement aEarlySendenStatement) {
		super(String.format("Заявление %s уже было отправлено ранее", aSendingStatement));
		_sendingStatement = null;
		_earlySendenStatement = aEarlySendenStatement;
	}

	/**
	 * @return the sendingStatement
	 */
	public StatementEntry getSendingStatement() {
		return _sendingStatement;
	}

	/**
	 * @return the earlySendenStatement
	 */
	public Statement getEarlySendenStatement() {
		return _earlySendenStatement;
	}

}
