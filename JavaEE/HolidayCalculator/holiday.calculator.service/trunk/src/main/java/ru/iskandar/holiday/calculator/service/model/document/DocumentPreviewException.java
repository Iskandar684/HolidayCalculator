package ru.iskandar.holiday.calculator.service.model.document;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;

/**
 * Исключение, связанное с ошибкой генерации документа
 */
public class DocumentPreviewException extends HolidayCalculatorException {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -1286894535472743377L;

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 * @param aCause
	 *            причина
	 */
	public DocumentPreviewException(String aMessage, Throwable aCause) {
		super(aMessage, aCause);
	}

	/**
	 * Конструктор
	 *
	 * @param aMessage
	 *            сообщение
	 */
	public DocumentPreviewException(String aMessage) {
		super(aMessage);
	}

}
