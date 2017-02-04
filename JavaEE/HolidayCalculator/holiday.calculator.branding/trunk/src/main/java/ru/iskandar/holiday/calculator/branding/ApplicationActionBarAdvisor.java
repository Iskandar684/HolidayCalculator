package ru.iskandar.holiday.calculator.branding;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/** ApplicationActionBarAdvisor */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	/** Действие "Помощь" */
	// private IWorkbenchAction _showHelpAction;

	/** Действие "Поиск" */
	// private IWorkbenchAction _searchHelpAction;

	/** Действие "Динамическая помощь" */
	// private IWorkbenchAction _dynamicHelpAction;

	/** Действие "О программе" */
	// private IWorkbenchAction _aboutAction;

	/** Действие "Открыть новое окно" */
	// private IWorkbenchAction _newWindowAction;

	/** Действие "Сохранить перспективу" */
	// private IWorkbenchAction _savePerspectiveAction;

	/** Действие "Сбросить перспективу" */
	// private IWorkbenchAction _resetPerspectiveAction;

	/** Действие "Добро пожаловать" */
	// private IWorkbenchAction _welcomeAction;

	// private IWorkbenchAction _showViewMenuAction;

	// private IContributionItem _openWindowsAction;

	/**
	 * Конструктор
	 * 
	 * @param aConfigurer
	 *            the action bar configurer
	 */
	public ApplicationActionBarAdvisor(IActionBarConfigurer aConfigurer) {
		super(aConfigurer);
	}

	/** {@inheritDoc} */
	@Override
	protected void makeActions(IWorkbenchWindow aWindow) {
		// _showHelpAction = ActionFactory.HELP_CONTENTS.create(aWindow);
		// register(_showHelpAction);
		// _searchHelpAction = ActionFactory.HELP_SEARCH.create(aWindow);
		// register(_searchHelpAction);
		// _dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(aWindow);
		// register(_dynamicHelpAction);
		// _aboutAction = ActionFactory.ABOUT.create(aWindow);
		// register(_aboutAction);
		// _newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(aWindow);
		// register(_newWindowAction);
		// _savePerspectiveAction =
		// ActionFactory.SAVE_PERSPECTIVE.create(aWindow);
		// register(_savePerspectiveAction);
		// _resetPerspectiveAction =
		// ActionFactory.RESET_PERSPECTIVE.create(aWindow);
		// register(_resetPerspectiveAction);

		// ActionFactory intro = ActionFactory.INTRO;

		// ActionFactory.
		// _showViewMenuAction = ActionFactory.SHOW_VIEW_MENU.create(aWindow);
		// ContributionItemFactory.VIEWS_SHORTLIST.create(aWindow);

		// register(_showViewMenuAction);
		// _openWindowsAction =
		// ContributionItemFactory.OPEN_WINDOWS.create(aWindow);

		// register(ActionFactory.SAVE.create(aWindow));
	}

	/** {@inheritDoc} */
	@Override
	protected void fillMenuBar(IMenuManager aMenuBar) {
		// MenuManager fileMenu =
		// new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		// // MenuManager helpMenu =
		// // new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		// MenuManager helpMenu =
		// new MenuManager("&Помощь", IWorkbenchActionConstants.M_HELP);

		// MenuManager help =
		// new MenuManager("&Помощь", IWorkbenchActionConstants.M_HELP);
		//
		// help.add(_openWindowsAction);
		// aMenuBar.add(help);
		// aMenuBar.add(fileMenu);
		// // Add a group marker indicating where action set menus will appear.
		// aMenuBar.add(new
		// GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		// aMenuBar.add(helpMenu);

		// File
		// fileMenu.add(newWindowAction);
		// fileMenu.add(new Separator());
		// fileMenu.add(messagePopupAction);
		// fileMenu.add(openViewAction);
		// fileMenu.add(new Separator());
		// fileMenu.add(exitAction);

		// Help
		// helpMenu.add(aboutAction);
		// helpMenu.add(showHelpAction);
		// helpMenu.add(searchHelpAction);
		// helpMenu.add(dynamicHelpAction);
		// helpMenu.add(aboutAction);

		aMenuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
	}

}
