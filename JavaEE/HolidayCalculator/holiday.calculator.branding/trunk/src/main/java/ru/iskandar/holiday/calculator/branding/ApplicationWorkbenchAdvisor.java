package ru.iskandar.holiday.calculator.branding;

import org.eclipse.ui.application.WorkbenchAdvisor;

/** ApplicationWorkbenchAdvisor */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	/** {@inheritDoc} */
	// @Override
	// public WorkbenchWindowAdvisor
	// createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer aConfigurer) {
	//
	// PlatformUI.getPreferenceStore().putValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS,
	// "false");
	//
	// PlatformUI.getPreferenceStore().putValue(IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS,
	// "true");
	//
	// return new ApplicationWorkbenchWindowAdvisor(aConfigurer);
	// }

	/** {@inheritDoc} */
	@Override
	public String getInitialWindowPerspectiveId() {
		return MainPerspective.ID;
	}

}
