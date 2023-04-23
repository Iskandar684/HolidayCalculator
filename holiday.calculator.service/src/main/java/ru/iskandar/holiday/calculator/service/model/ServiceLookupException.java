package ru.iskandar.holiday.calculator.service.model;

/**
 * Исключение получения сервиса
 */
public class ServiceLookupException extends RuntimeException {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = -4003179389372973584L;

	/**
	 * Конструктор
	 * 
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public ServiceLookupException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

}
