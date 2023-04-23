package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.concurrent.Callable;

import ru.iskandar.holiday.calculator.ui.ModelProvider;
import ru.iskandar.holiday.calculator.ui.document.HTMLContent;
import ru.iskandar.holiday.calculator.ui.document.IHTMLContentProvider;

/**
 *
 */
public class HTMLContentProvider extends ModelProvider<HTMLContent> implements IHTMLContentProvider {

	/**
	 * Конструктор
	 */
	public HTMLContentProvider(Callable<HTMLContent> aLoader) {
		super(aLoader);
	}

	@Override
	public HTMLContent getContent() {
		return getModel();
	}
}
