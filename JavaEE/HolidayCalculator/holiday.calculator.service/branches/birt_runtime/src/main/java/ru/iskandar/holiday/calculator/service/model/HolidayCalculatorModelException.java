/**
 *
 */
package ru.iskandar.holiday.calculator.service.model;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;

/**
 * Исключение модели учета отгулов
 */
public class HolidayCalculatorModelException extends HolidayCalculatorException {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = 9095047457184922529L;

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public HolidayCalculatorModelException(String aMessage, Exception aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 */
	public HolidayCalculatorModelException(String aMessage) {
		super(aMessage);
	}
}
