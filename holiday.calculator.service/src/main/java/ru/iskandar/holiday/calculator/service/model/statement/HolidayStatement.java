package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Set;

/**
 * Заявление на отгул
 */
public class HolidayStatement extends Statement<HolidayStatementEntry> {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -4442838042007056450L;

	/**
	 * Конструктор
	 *
	 * @param aId
	 *            идентификатор
	 */
	public HolidayStatement(StatementId aId, HolidayStatementEntry aEntry) {
		super(aId, aEntry);
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return getEntry().getDays();
	}

}
