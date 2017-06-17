package ru.iskandar.holiday.calculator.service.model.document;

import java.io.Serializable;

/**
 * Документ заявления
 */
public class StatementDocument implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -6104154983338677906L;

	/** Содержимое */
	private final byte[] _content;

	/**
	 * Конструктор
	 *
	 * @param aContent
	 *            содержимое документа
	 */
	public StatementDocument(byte[] aContent) {
		_content = aContent;
	}

	/**
	 * Возвращает содержимое документа
	 *
	 * @return содержимое документа
	 */
	public byte[] getContent() {
		return _content;
	}

}
