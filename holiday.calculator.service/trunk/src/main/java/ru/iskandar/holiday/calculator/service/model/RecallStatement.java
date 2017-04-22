package ru.iskandar.holiday.calculator.service.model;

import java.util.Date;
import java.util.Set;

/**
 * Заявления на отзыв. Это заявление, где указаны дни, в которые сотрудник
 * работал во время отпуска. На основе этих дней зачислиются отгула.
 */
public class RecallStatement extends Statement<RecallStatementEntry> {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -6055526811135206004L;

	/**
	 * Конструктор
	 *
	 * @param aID
	 *            идентификатор заявления
	 */
	public RecallStatement(StatementId aID, RecallStatementEntry aEntry) {
		super(aID, aEntry);
	}

	/**
	 * @return the recallDates
	 */
	public Set<Date> getRecallDates() {
		return getEntry().getRecallDates();
	}

}
