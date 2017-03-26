package ru.iskandar.holiday.calculator.ui.menu;

import java.util.Objects;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import ru.iskandar.holiday.calculator.service.model.HolidayStatementSendedEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
 * Элемент меню "Входящие заявления"
 */
public class IncomingStatementsContributionItem extends ContributionItem {

	private final HolidayCalculatorModelProvider _provider;

	private final MenuManager _menuManager;

	public IncomingStatementsContributionItem(HolidayCalculatorModelProvider aProvider, MenuManager aMenuManager) {
		Objects.requireNonNull(aProvider);
		_menuManager = aMenuManager;
		_provider = aProvider;
		_provider.addListener(new HolidayCalculatorModelListener());
		_provider.addLoadListener(new ILoadListener() {

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
						IncomingStatementsContributionItem.this.update();
					}

				});

			}
		});
		update();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		setVisible(mustBeVisible());
		IContributionItem[] items = _menuManager.getItems();
		_menuManager.update(true);
		_menuManager.removeAll();
		for (IContributionItem item : items) {
			_menuManager.add(item);
		}
	}

	private boolean mustBeVisible() {
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
		boolean visible = false;
		if (LoadStatus.LOADED.equals(provider.getLoadStatus())) {
			visible = provider.getModel().canConsider();
		}
		return visible;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fill(Menu aMenu, int aIndex) {
		MenuItem item = new MenuItem(aMenu, SWT.NONE);
		new IncomingStatementsMenuItemPM(item, _provider);
	}

	private class HolidayCalculatorModelListener implements IHolidayCalculatorModelListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void holidayStatementSended(HolidayStatementSendedEvent aAEvent) {
			Display.getDefault().asyncExec(new Runnable() {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void run() {
					IncomingStatementsContributionItem.this.update();
				}

			});

		}

	}

}
