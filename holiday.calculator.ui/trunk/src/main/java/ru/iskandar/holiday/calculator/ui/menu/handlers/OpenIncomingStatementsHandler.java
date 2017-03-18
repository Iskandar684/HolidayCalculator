package ru.iskandar.holiday.calculator.ui.menu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
 * Обработчик элемента меню открытия редактора входящих заявления
 */
public class OpenIncomingStatementsHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
		boolean enabled = false;
		if (LoadStatus.LOADED.equals(provider.getLoadStatus())) {
			enabled = provider.getModel().canConsider();
		}
		return enabled;
	}

}
