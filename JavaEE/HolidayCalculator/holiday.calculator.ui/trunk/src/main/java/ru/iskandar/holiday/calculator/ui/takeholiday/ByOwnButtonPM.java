package ru.iskandar.holiday.calculator.ui.takeholiday;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementType;

/**
 * Контроллер кнопки "За свой счет"
 *
 */
public class ByOwnButtonPM {

	/**
	 * Конструктор
	 */
	public ByOwnButtonPM(Button aButton, final TakeHolidayStatementBuilder aStatementBuilder) {
		boolean selection = HolidayStatementType.BY_OWN.equals(aStatementBuilder.getStatementType());
		aButton.setSelection(selection);
		aButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				aStatementBuilder.setStatementType(HolidayStatementType.BY_OWN);
			}
		});
	}

}
