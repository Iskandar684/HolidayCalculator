package ru.iskandar.holiday.calculator.service.model;

import java.util.Date;
import java.util.Set;

/**
 * Заявление на отгул
 */
public class HolidayStatement extends Statement {

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
	public HolidayStatement(StatementId aId, StatementEntry aEntry) {
		super(aId, aEntry);
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return ((HolidayStatementEntry) getEntry()).getDays();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatementType getStatementType() {
		// TODO не переопределять
		return StatementType.HOLIDAY_STATEMENT;
	}

}
