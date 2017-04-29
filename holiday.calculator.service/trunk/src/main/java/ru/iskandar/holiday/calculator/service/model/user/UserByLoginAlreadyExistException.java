package ru.iskandar.holiday.calculator.service.model.user;

/**
 *
 */
public class UserByLoginAlreadyExistException extends Exception {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 1332882088835282693L;

	public UserByLoginAlreadyExistException(String aMessage) {
		super(aMessage);
	}

}
