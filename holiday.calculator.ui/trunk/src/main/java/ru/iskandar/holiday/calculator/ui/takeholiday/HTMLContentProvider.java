/**
 *
 */
package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.Objects;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.document.DocumentPreviewException;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.ui.Activator;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.document.IHTMLContentProvider;

/**
 *
 */
public class HTMLContentProvider implements IHTMLContentProvider {

	/** Пустой массив */
	private static final byte[] EMPTY_CONTENT = new byte[0];

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public HTMLContentProvider(HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_modelProvider = aHolidayCalculatorModelProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getContent() {
		LoadStatus loadStatus = _modelProvider.getLoadStatus();
		if (LoadStatus.LOADED.equals(loadStatus)) {
			TakeHolidayStatementBuilder builder = _modelProvider.getModel().getHolidayStatementBuilder();
			try {
				StatementDocument document = builder.preview();
				return document.getContent();
			} catch (DocumentPreviewException e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"Ошибка генерации предварительного просмотра заявления на отгул", e));
			}
		}
		return EMPTY_CONTENT;
	}
}
