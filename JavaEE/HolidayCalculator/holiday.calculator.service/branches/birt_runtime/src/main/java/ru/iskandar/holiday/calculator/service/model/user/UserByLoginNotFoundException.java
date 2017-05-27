/**
 *
 */
package ru.iskandar.holiday.calculator.service.model.user;

/**
 *
 */
public class UserByLoginNotFoundException extends RuntimeException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 1489838628225495290L;

	public UserByLoginNotFoundException(String message) {
		super(message);
	}

}
