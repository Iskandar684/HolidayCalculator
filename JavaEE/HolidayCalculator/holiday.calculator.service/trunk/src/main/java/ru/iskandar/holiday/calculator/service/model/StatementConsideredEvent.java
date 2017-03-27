package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

/**
 * Событие о рассмотрении заявления
 */
public class StatementConsideredEvent extends HolidayCalculatorEvent {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 3584242181985152673L;

	/** Заявление, связанное с событием */
	private final Statement _affectedStatement;

	/**
	 * Конструктор
	 */
	public StatementConsideredEvent(Statement aHolidayStatement) {
		Objects.requireNonNull(aHolidayStatement);
		_affectedStatement = aHolidayStatement;
	}

	/**
	 * @return the affectedStatement
	 */
	public Statement getAffectedStatement() {
		return _affectedStatement;
	}

}
