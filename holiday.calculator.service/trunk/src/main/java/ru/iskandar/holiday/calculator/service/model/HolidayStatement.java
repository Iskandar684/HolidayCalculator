package ru.iskandar.holiday.calculator.service.model;

import java.util.UUID;

/**
 * Заявление на отгул
 */
public class HolidayStatement extends Statement {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -4442838042007056450L;

	/**
	 * @param aUUID
	 */
	public HolidayStatement(UUID aUUID) {
		super(aUUID);
	}

}
