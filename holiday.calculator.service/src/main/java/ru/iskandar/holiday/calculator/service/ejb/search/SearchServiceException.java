package ru.iskandar.holiday.calculator.service.ejb.search;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;

/**
 * Исключение сервиса поиска.
 */
public class SearchServiceException extends HolidayCalculatorException {

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = 7100613751875863407L;

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public SearchServiceException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 */
	public SearchServiceException(String aMessage) {
		super(aMessage);
	}

}
