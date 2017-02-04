package ru.iskandar.holiday.calculator.branding;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/** ApplicationWorkbenchWindowAdvisor */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	/**
	 * Конструктор
	 * 
	 * @param aConfigurer
	 *            конфигуратор
	 */
	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer aConfigurer) {
		super(aConfigurer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer aConfigurer) {
		return new ApplicationActionBarAdvisor(aConfigurer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preWindowOpen() {
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setShowProgressIndicator(true);
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(true);
		configurer.setShowPerspectiveBar(false);
		configurer.setShowProgressIndicator(true);
		// configurer.setShowFastViewBars(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preWindowShellClose() {
		return MessageDialog.openQuestion(getWindowConfigurer().getWindow().getShell(), "Подтверждение выхода",
				"Закрыть приложение?");
	}

}
