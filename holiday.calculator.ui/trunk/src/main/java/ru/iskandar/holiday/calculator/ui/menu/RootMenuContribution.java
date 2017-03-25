package ru.iskandar.holiday.calculator.ui.menu;

import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;
import ru.iskandar.holiday.calculator.ui.menu.handlers.TakeHolidayAction;

public class RootMenuContribution extends ExtensionContributionFactory {

	public RootMenuContribution() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createContributionItems(IServiceLocator aServiceLocator, IContributionRoot aAdditions) {
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
		IncomingStatementsContributionItem incomingContributionItem = new IncomingStatementsContributionItem();
		MenuManager menuManager = new MenuManager();
		menuManager.add(incomingContributionItem);
		menuManager.add(new TakeHolidayAction(provider));
		new StatementsMenuPM(menuManager, provider);

		aAdditions.addContributionItem(menuManager, Expression.TRUE);
	}

}
