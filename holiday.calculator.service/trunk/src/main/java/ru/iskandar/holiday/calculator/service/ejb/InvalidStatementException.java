package ru.iskandar.holiday.calculator.service.ejb;

/**
 * Исключение для случае, когда заявление невалидное (Например, отсутствует
 * автор или дата)
 */
public class InvalidStatementException extends RuntimeException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 4420127650096223944L;

	/**
	 * Конструктор
	 * 
	 * @param aMessage
	 *            сообщение
	 */
	public InvalidStatementException(String aMessage) {
		super(aMessage);
	}

}
