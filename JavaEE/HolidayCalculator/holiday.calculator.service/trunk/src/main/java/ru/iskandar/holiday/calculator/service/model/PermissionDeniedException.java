package ru.iskandar.holiday.calculator.service.model;

/**
 * Исключение, связанное отсутствием прав на некоторое действие
 */
public class PermissionDeniedException extends RuntimeException {

	/**
	 * Идентификатор
	 */
	private static final long serialVersionUID = -3928500634114845963L;

	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionDeniedException(String message) {
		super(message);
	}

}
