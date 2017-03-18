package ru.iskandar.holiday.calculator.ui.menu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;
import ru.iskandar.holiday.calculator.ui.takeholiday.TakeHolidayEditor;
import ru.iskandar.holiday.calculator.ui.takeholiday.TakeHolidayEditorInput;

/**
 * Обработчик элемента меню подачи заявления на отгул
 */
public class TakeHolidayMenuHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new TakeHolidayEditorInput(provider), TakeHolidayEditor.EDITOR_ID, true, IWorkbenchPage.MATCH_ID);
		} catch (PartInitException e) {
			throw new ExecutionException("Ошибка открытия формы подачи заявления на отгул", e);
		}

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
			enabled = provider.getModel().canCreateHolidayStatementBuilder();
		}
		return enabled;
	}

}
