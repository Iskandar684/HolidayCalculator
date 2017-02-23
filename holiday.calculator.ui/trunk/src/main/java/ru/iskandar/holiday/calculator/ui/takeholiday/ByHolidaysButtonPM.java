package ru.iskandar.holiday.calculator.ui.takeholiday;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import ru.iskandar.holiday.calculator.service.model.HolidayStatementType;
import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;

/**
 * Контроллер кнопки "За счет отгулов"
 *
 */
public class ByHolidaysButtonPM {

	/**
	 * Конструктор
	 */
	public ByHolidaysButtonPM(Button aButton, final TakeHolidayStatementBuilder aStatementBuilder) {
		boolean selection = HolidayStatementType.BY_HOLIDAY_DAYS.equals(aStatementBuilder.getStatementType());
		aButton.setSelection(selection);
		aButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				aStatementBuilder.setStatementType(HolidayStatementType.BY_HOLIDAY_DAYS);
			}
		});
	}

}
