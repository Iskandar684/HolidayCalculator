package ru.iskandar.holiday.calculator.service.ejb;

/**
 * Базовое исключение сервиса учета отгулов
 */
public class HolidayCalculatorException extends Exception {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 6418817234419893147L;

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public HolidayCalculatorException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 */
	public HolidayCalculatorException(String aMessage) {
		super(aMessage);
	}

}
