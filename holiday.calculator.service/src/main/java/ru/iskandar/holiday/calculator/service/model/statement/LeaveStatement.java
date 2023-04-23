package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Set;

/**
 * Заявление на отпуск
 */
public class LeaveStatement extends Statement<LeaveStatementEntry> {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 553333168079346415L;

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор заявления
	 * @param aAuthor
	 *            автор заявления
	 */
	public LeaveStatement(StatementId aID, LeaveStatementEntry aEntry) {
		super(aID, aEntry);
	}

	public Set<Date> getLeaveDates() {
		return getEntry().getLeaveDates();
	}

}
