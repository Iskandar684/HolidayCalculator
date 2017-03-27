package ru.iskandar.holiday.calculator.service.ejb;

/**
 * Исключение для случая, когда заявление в системе не найдено
 */
public class StatementNotFoundException extends RuntimeException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -5501909108630888360L;

	/**
	 *
	 */
	public StatementNotFoundException(String aString) {
		super(aString);
	}

}
