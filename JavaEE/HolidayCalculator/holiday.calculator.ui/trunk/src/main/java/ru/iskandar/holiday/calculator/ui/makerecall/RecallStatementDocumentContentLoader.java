package ru.iskandar.holiday.calculator.ui.makerecall;

import java.util.Objects;
import java.util.concurrent.Callable;

import ru.iskandar.holiday.calculator.service.model.MakeRecallStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.document.HTMLContent;

/**
 *
 */
public class RecallStatementDocumentContentLoader implements Callable<HTMLContent> {

	private final HolidayCalculatorModelProvider _holidayCalculatorModelProvider;

	/**
	 * Конструктор
	 */
	public RecallStatementDocumentContentLoader(HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_holidayCalculatorModelProvider = aHolidayCalculatorModelProvider;
	}

	@Override
	public HTMLContent call() throws Exception {
		MakeRecallStatementBuilder builder = _holidayCalculatorModelProvider.getModel().getRecallStatementBuilder();
		StatementDocument document = builder.preview();
		return new HTMLContent(document.getContent());
	}

}
