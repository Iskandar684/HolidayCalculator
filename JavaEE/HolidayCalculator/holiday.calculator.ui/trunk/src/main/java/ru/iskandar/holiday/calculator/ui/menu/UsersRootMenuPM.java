package ru.iskandar.holiday.calculator.ui.menu;

import java.util.Objects;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Display;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.menu.handlers.CreateUserAction;
import ru.iskandar.holiday.calculator.ui.menu.handlers.ViewUsersAction;

/**
 * Контроллер меню "Заявления"
 */
public class UsersRootMenuPM {

	private final MenuManager _menuManager;

	private final ContributionItem _createUserMenuItem;

	private final ContributionItem _viewUsersMenuItem;

	private final HolidayCalculatorModelProvider _modelProvider;

	UsersRootMenuPM(MenuManager aMenuManager, final HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aMenuManager);
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_menuManager = aMenuManager;
		_modelProvider = aHolidayCalculatorModelProvider;
		final HolidayCalculatorModelListener modelListener = new HolidayCalculatorModelListener();
		aHolidayCalculatorModelProvider.addListener(modelListener);
		aHolidayCalculatorModelProvider.addLoadListener(new LoadListener());
		_createUserMenuItem = new ActionContributionItem(new CreateUserAction(aHolidayCalculatorModelProvider));
		_viewUsersMenuItem = new ActionContributionItem(new ViewUsersAction(aHolidayCalculatorModelProvider));
		aMenuManager.add(_createUserMenuItem);
		aMenuManager.add(_viewUsersMenuItem);
		update();
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
		boolean canCreateUser = false;
		boolean canViewUsers = false;
		if (LoadStatus.LOADED.equals(_modelProvider.getLoadStatus())) {
			canCreateUser = _modelProvider.getModel().canCreateUser();
			canViewUsers = _modelProvider.getModel().canViewUsers();
		}
		_createUserMenuItem.setVisible(canCreateUser);
		_viewUsersMenuItem.setVisible(canViewUsers);
		_menuManager.setVisible(canCreateUser || canViewUsers);
		_menuManager.update(true);
		IContributionManager parent = _menuManager.getParent();
		if (parent != null) {
			parent.update(true);
		}
	}

	private String getText() {
		return Messages.usersRootMenuText;
	}

}
