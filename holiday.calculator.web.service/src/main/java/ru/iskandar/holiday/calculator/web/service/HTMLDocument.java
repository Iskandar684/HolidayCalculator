package ru.iskandar.holiday.calculator.web.service;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * HTML документ
 */
@XmlRootElement(name = "HTMLDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class HTMLDocument implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -6104154983338677906L;

	/** Содержимое */
	@XmlElement(name = "content")
	private final String _content;

	/**
	 * Конструктор
	 *
	 * @param aContent
	 *            содержимое документа
	 */
	public HTMLDocument(String aContent) {
		_content = aContent;
	}

	/**
	 * Возвращает содержимое документа
	 *
	 * @return содержимое документа
	 */
	public String getContent() {
		return _content;
	}

}
