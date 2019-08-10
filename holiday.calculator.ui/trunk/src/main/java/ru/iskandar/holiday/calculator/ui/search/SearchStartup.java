package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Обработчик открытия панели поиска после запуска клиента.
 */
public class SearchStartup implements IStartup {

	@Override
	public void earlyStartup() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> openSearchView());
	}

	/**
	 * Открывает панель поиска.
	 */
	private void openSearchView() {
		IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = (ww == null) ? null : ww.getActivePage();
		try {
			page.openEditor(new SearchEditorInput(), SearchEditor.ID);
		} catch (PartInitException e) {
			IStatus status = new Status(IStatus.ERROR, getClass().getName(), "Ошибка открытия панели поиска.", e);
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		}
	}

}
