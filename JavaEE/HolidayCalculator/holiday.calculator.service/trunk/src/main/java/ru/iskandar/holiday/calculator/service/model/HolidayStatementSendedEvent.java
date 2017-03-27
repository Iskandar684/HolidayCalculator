package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

/**
 * Событие о подачи заявления
 */
public class HolidayStatementSendedEvent extends HolidayCalculatorEvent {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -3835067673271539642L;

	/** Заявление, связанное с событием */
	private final HolidayStatement _affectedStatement;

	/**
	 * Конструктор
	 */
	public HolidayStatementSendedEvent(HolidayStatement aHolidayStatement) {
		Objects.requireNonNull(aHolidayStatement);
		_affectedStatement = aHolidayStatement;
	}

	/**
	 * @return the affectedStatement
	 */
	public HolidayStatement getAffectedStatement() {
		return _affectedStatement;
	}

}
