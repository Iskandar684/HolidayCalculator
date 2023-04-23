package ru.iskandar.holiday.calculator.ui.document;

import ru.iskandar.holiday.calculator.ui.ILoadingProvider;

/**
 * Поставщик содержания документа
 */
public interface IHTMLContentProvider extends ILoadingProvider {

	/**
	 * Возвращает содержание документа
	 *
	 * @return содержание документа
	 */
	public HTMLContent getContent();

}
