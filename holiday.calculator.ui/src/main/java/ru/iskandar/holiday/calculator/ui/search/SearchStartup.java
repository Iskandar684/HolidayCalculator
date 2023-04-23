package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
 * Обработчик открытия панели поиска после запуска клиента.
 */
public class SearchStartup implements IStartup {

	@Override
	public void earlyStartup() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(() -> {
			openSearchView();
			display.addFilter(SWT.KeyDown, e -> {
				if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'f')) {
					openSearchView();
				}
			});
		});
	}

	/**
	 * Открывает панель поиска.
	 */
	private void openSearchView() {
		IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = (ww == null) ? null : ww.getActivePage();
		try {
			HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
			page.openEditor(new SearchEditorInput(provider), SearchEditor.ID, true, IWorkbenchPage.MATCH_ID);
		} catch (PartInitException e) {
			IStatus status = new Status(IStatus.ERROR, getClass().getName(), "Ошибка открытия панели поиска.", e);
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		}
	}

}
