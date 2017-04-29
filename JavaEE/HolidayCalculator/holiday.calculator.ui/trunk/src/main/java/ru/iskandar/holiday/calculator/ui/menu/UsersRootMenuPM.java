package ru.iskandar.holiday.calculator.ui.menu;

import java.util.Objects;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Display;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Контроллер меню "Заявления"
 */
public class UsersRootMenuPM {

	private final MenuManager _menuManager;

	UsersRootMenuPM(MenuManager aMenuManager, final HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aMenuManager);
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_menuManager = aMenuManager;
		update();
		final HolidayCalculatorModelListener modelListener = new HolidayCalculatorModelListener();
		aHolidayCalculatorModelProvider.addListener(modelListener);
		aHolidayCalculatorModelProvider.addLoadListener(new LoadListener());
	}

	private class LoadListener implements ILoadListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void loadStatusChanged() {
			Display.getDefault().asyncExec(new Runnable() {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void run() {
					UsersRootMenuPM.this.update();

				}

			});

		}

	}

	private class HolidayCalculatorModelListener implements IHolidayCalculatorModelListener {

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
					UsersRootMenuPM.this.update();

				}

			});

		}

	}

	private void update() {
		_menuManager.setMenuText(getText());
		_menuManager.update(IAction.TEXT);
	}

	private String getText() {
		return Messages.usersRootMenuText;
	}

}
