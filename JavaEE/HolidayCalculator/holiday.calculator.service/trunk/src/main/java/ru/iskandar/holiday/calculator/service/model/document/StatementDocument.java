package ru.iskandar.holiday.calculator.service.model.document;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Документ заявления
 */
@XmlRootElement(name = "StatementDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatementDocument implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -6104154983338677906L;

	/** Содержимое */
	@XmlElement(name = "content")
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
