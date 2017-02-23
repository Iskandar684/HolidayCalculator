package ru.iskandar.holiday.calculator.ui.takeholiday;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelException;
import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;

/**
 * Контроллер кнопки подачи заявления на отгул
 *
 */
public class SendHolidayStatementButtonPM {

	/** Кнопка */
	private final Button _button;
	/** Формирователь заявления */
	private final TakeHolidayStatementBuilder _statementBuilder;

	/**
	 * Конструктор
	 */
	public SendHolidayStatementButtonPM(Button aButton, final TakeHolidayStatementBuilder aStatementBuilder) {
		_button = aButton;
		_statementBuilder = aStatementBuilder;
		aButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				try {
					aStatementBuilder.sendHolidayStatement();
				} catch (HolidayCalculatorModelException e) {
					throw new IllegalStateException("Ошибка подачи заявления на отгул", e);
				}
			}
		});
		update();
	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		_button.setEnabled(_statementBuilder.canSendHolidayStatement());
	}

}
