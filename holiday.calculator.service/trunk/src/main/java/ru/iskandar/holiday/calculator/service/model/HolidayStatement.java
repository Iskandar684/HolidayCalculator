package ru.iskandar.holiday.calculator.service.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Заявление на отгул
 */
public class HolidayStatement extends Statement {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -4442838042007056450L;

	/** Дата отгула */
	private final Set<Date> _days;

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор
	 */
	public HolidayStatement(UUID aUUID, Set<Date> aDays, User aAuthor) {
		super(aUUID, aAuthor);
		Objects.requireNonNull(aDays);
		_days = new HashSet<>(aDays);
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return Collections.unmodifiableSet(_days);
	}

}
