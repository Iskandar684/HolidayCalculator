package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.Objects;
import java.util.concurrent.Callable;

import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.document.HTMLContent;

/**
 *
 */
public class HolidayStatementDocumentContentLoader implements Callable<HTMLContent> {

	private final HolidayCalculatorModelProvider _holidayCalculatorModelProvider;

	/**
	 * Конструктор
	 */
	public HolidayStatementDocumentContentLoader(HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_holidayCalculatorModelProvider = aHolidayCalculatorModelProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HTMLContent call() throws Exception {
		TakeHolidayStatementBuilder builder = _holidayCalculatorModelProvider.getModel().getHolidayStatementBuilder();
		StatementDocument document = builder.preview();
		return new HTMLContent(document.getContent());
	}

}
