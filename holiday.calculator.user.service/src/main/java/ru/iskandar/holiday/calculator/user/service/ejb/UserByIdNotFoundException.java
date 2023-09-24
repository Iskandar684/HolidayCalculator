package ru.iskandar.holiday.calculator.user.service.ejb;

/**
 *
 */
public class UserByIdNotFoundException extends RuntimeException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 3175712490342357128L;

	public UserByIdNotFoundException(String message) {
		super(message);
	}

	public UserByIdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
