/**
 *
 */
package ru.iskandar.holiday.calculator.ui.menu;

import java.util.Objects;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.MenuItem;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
 *
 */
public class IncomingStatementsMenuItemPM {

	private final MenuItem _item;

	IncomingStatementsMenuItemPM(MenuItem aItem) {
		Objects.requireNonNull(aItem);
		_item = aItem;
		update();
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
