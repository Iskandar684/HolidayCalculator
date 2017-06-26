package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.statement.StatementEntry;

/**
 * Событие изменения содержания заявления
 */
public class StatementContentChangedEvent extends HolidayCalculatorEvent {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 1471930739712522667L;

	private final StatementEntry _statementEntry;

	/**
	 *
	 */
	public StatementContentChangedEvent(StatementEntry aEntry) {
		Objects.requireNonNull(aEntry);
		_statementEntry = aEntry;
	}

	public StatementEntry getStatementEntry() {
		return _statementEntry;
	}

}
