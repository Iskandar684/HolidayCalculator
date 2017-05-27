/**
 *
 */
package ru.iskandar.holiday.calculator.service.model;

/**
 * Исключение загрузки модели учета отгулов
 */
public class HolidayCalculatorModelLoadException extends Exception {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = 7131288036330253863L;

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public HolidayCalculatorModelLoadException(String aMessage, Exception aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 */
	public HolidayCalculatorModelLoadException(String aMessage) {
		super(aMessage);
	}
}
