package ru.iskandar.holiday.calculator.ui.menu.handlers;

import java.util.Objects;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Обработчик элемента меню создания пользователя
 */
public class CreateUserAction extends Action {

	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public CreateUserAction(HolidayCalculatorModelProvider aProvider) {
		Objects.requireNonNull(aProvider);
		_modelProvider = aProvider;
		setText(Messages.createUserMenuItem);
		aProvider.addListener(new IHolidayCalculatorModelListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(HolidayCalculatorEvent aAEvent) {
				Display.getDefault().asyncExec(new Runnable() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						update();
					}
				});

			}
		});
		_modelProvider.addLoadListener(new ILoadListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void loadStatusChanged() {
				update();
			}
		});
		update();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		CreateUserDialog dialog = new CreateUserDialog(Display.getDefault().getActiveShell(), _modelProvider);
		dialog.open();
	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		boolean enabled = false;
		if (LoadStatus.LOADED.equals(_modelProvider.getLoadStatus())) {
			enabled = _modelProvider.getModel().canCreateUser();
		}
		setEnabled(enabled);
	}

}
