package ru.iskandar.holiday.calculator.ui.takeleave;

import java.util.Objects;
import java.util.concurrent.Callable;

import ru.iskandar.holiday.calculator.service.model.TakeLeaveStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.document.HTMLContent;

/**
 *
 */
public class LeaveStatementDocumentContentLoader implements Callable<HTMLContent> {

	private final HolidayCalculatorModelProvider _holidayCalculatorModelProvider;

	/**
	 * Конструктор
	 */
	public LeaveStatementDocumentContentLoader(HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_holidayCalculatorModelProvider = aHolidayCalculatorModelProvider;
	}

	@Override
	public HTMLContent call() throws Exception {
		TakeLeaveStatementBuilder builder = _holidayCalculatorModelProvider.getModel().getLeaveStatementBuilder();
		StatementDocument document = builder.preview();
		return new HTMLContent(document.getContent());
	}

}
