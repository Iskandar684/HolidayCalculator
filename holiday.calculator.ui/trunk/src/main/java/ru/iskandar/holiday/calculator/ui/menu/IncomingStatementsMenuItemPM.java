package ru.iskandar.holiday.calculator.ui.menu;

import java.util.Objects;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;

import ru.iskandar.holiday.calculator.service.model.HolidayStatementSendedEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
 * Контроллер пункта меню "Входящие заявления"
 */
public class IncomingStatementsMenuItemPM {

	private final MenuItem _item;

	IncomingStatementsMenuItemPM(MenuItem aItem, final HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aItem);
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_item = aItem;
		update();
		final HolidayCalculatorModelListener modelListener = new HolidayCalculatorModelListener();
		aHolidayCalculatorModelProvider.addListener(modelListener);
		aItem.addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				aHolidayCalculatorModelProvider.removeListener(modelListener);
			}

		});
		aHolidayCalculatorModelProvider.addLoadListener(new ILoadListener() {

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
						IncomingStatementsMenuItemPM.this.update();
					}

				});

			}
		});
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
					IncomingStatementsMenuItemPM.this.update();

				}

			});

		}

	}

	private void update() {
		_item.setText(getText());
		_item.setEnabled(isEnabled());
	}

	private String getText() {
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
		if (LoadStatus.LOADED.equals(provider.getLoadStatus())) {
			if (provider.getModel().canConsider()) {
				int count = provider.getModel().getUnConsideredStatementsCount();
				if (count != 0) {
					return NLS.bind(Messages.openIncomingStatementsMenuItemWithCount, count);
				}
			}
		}
		return Messages.openIncomingStatementsMenuItem;
	}

	private boolean isEnabled() {
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
		boolean enabled = false;
		if (LoadStatus.LOADED.equals(provider.getLoadStatus())) {
			enabled = provider.getModel().canConsider();
		}
		return enabled;
	}

}
