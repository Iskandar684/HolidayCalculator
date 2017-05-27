package ru.iskandar.holiday.calculator.service.model;

/**
 * Исключение инициализации модели учета отгулов
 */
public class HolidayCalculatorModelInitException extends HolidayCalculatorModelException {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = -2333727961211502741L;

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public HolidayCalculatorModelInitException(String aMessage, Exception aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 */
	public HolidayCalculatorModelInitException(String aMessage) {
		super(aMessage);
	}
}
