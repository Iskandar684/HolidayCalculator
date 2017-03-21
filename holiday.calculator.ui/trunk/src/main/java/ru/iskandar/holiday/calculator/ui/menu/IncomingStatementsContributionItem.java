package ru.iskandar.holiday.calculator.ui.menu;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
  *
 */
public class IncomingStatementsContributionItem extends ContributionItem {

	/**
	 * Конструктор
	 */
	public IncomingStatementsContributionItem() {
	}

	/**
	 * Конструктор
	 *
	 * @param aId
	 *            идентификатор
	 */
	public IncomingStatementsContributionItem(String aId) {
		super(aId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fill(Menu aMenu, int aIndex) {
		MenuItem item = new MenuItem(aMenu, aIndex);
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
		new IncomingStatementsMenuItemPM(item, provider);
	}

}
