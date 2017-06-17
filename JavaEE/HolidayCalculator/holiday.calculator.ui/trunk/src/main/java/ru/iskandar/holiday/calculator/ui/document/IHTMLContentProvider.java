package ru.iskandar.holiday.calculator.ui.document;

/**
 * Поставщик содержания документа
 */
public interface IHTMLContentProvider {

	/**
	 * Возвращает содержание документа
	 * 
	 * @return содержание документа
	 */
	public byte[] getContent();

}
