/**
 *
 */
package ru.iskandar.holiday.calculator.ui;

import java.util.Objects;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

/**
 * Контроллер кнопки формирования заявления на отгул
 *
 */
public class TakeHolidayButtonPM {

	/** Кнопка */
	private final Button _button;

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _provider;

	/**
	 * Конструктор
	 */
	public TakeHolidayButtonPM(Button aButton, HolidayCalculatorModelProvider aProvider) {
		Objects.requireNonNull(aButton);
		Objects.requireNonNull(aProvider);
		_button = aButton;
		_provider = aProvider;
		aButton.addSelectionListener(new BtSelectionListener());
		update();
	}

	/**
	 * Обновляет кнопку
	 */
	private void update() {
		_button.setEnabled(_provider.getModel().canCreateHolidayStatementBuilder());
	}

	/**
	 * Обработчик нажатия кнопки
	 */
	private class BtSelectionListener extends SelectionAdapter {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			_provider.getModel().createHolidayStatementBuilder();
		}

	}

}
