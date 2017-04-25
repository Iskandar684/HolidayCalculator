package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.statement.Statement;

/**
 * Событие о подачи заявления
 */
public class StatementSendedEvent extends HolidayCalculatorEvent {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -3835067673271539642L;

	/** Заявление, связанное с событием */
	private final Statement<?> _affectedStatement;

	/**
	 * Конструктор
	 */
	public StatementSendedEvent(Statement<?> aHolidayStatement) {
		Objects.requireNonNull(aHolidayStatement);
		_affectedStatement = aHolidayStatement;
	}

	/**
	 * @return the affectedStatement
	 */
	public Statement<?> getAffectedStatement() {
		return _affectedStatement;
	}

}
