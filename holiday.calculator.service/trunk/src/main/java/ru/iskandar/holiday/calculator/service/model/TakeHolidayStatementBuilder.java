package ru.iskandar.holiday.calculator.service.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Формирователь заявления на отгул
 */
public class TakeHolidayStatementBuilder {

	/** Тип заявления на отгул */
	private HolidayStatementType _statementType = HolidayStatementType.BY_HOLIDAY_DAYS;
	/** Дни на отгул */
	private final Set<Date> _days = new HashSet<>();

	/**
	 * Конструктор
	 */
	TakeHolidayStatementBuilder() {
	}

	/**
	 * Подает заявление на отгул
	 *
	 * @throws HolidayCalculatorModelException
	 *             ошибка подачи заявления на отгул
	 */
	public void sendHolidayStatement() throws HolidayCalculatorModelException {
		if (!canSendHolidayStatement()) {
			throw new HolidayCalculatorModelException("Подача заявления на отгул запрещено");
		}
	}

	/**
	 * Возвращает возможность подачи заявления на отгул
	 *
	 * @return {@code true}, если подача заявления на отгул разрешено
	 */
	public boolean canSendHolidayStatement() {
		return true;
	}

	/**
	 * @return the statementType
	 */
	public HolidayStatementType getStatementType() {
		return _statementType;
	}

	/**
	 * @param aStatementType
	 *            the statementType to set
	 */
	public void setStatementType(HolidayStatementType aStatementType) {
		_statementType = aStatementType;
	}

	public void addDate(Date aDate) {
		_days.add(aDate);
	}

	public void removeDate(Date aDate) {
		_days.remove(aDate);
	}

	public void clearDates() {
		_days.clear();
	}

}
