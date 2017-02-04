package ru.iskandar.holiday.calculator.branding;

import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/** ApplicationWorkbenchAdvisor */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    /** Идентификатор */
    private static final String PERSPECTIVE_ID =
            "scanner.rcp.product.MainPerspective"; //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer aConfigurer) {

        PlatformUI.getPreferenceStore().putValue(
                IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS,
                "false");

        PlatformUI.getPreferenceStore().putValue(
                IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS, "true");

        return new ApplicationWorkbenchWindowAdvisor(aConfigurer);
    }

    /** {@inheritDoc} */
    @Override
    public String getInitialWindowPerspectiveId() {
        return PERSPECTIVE_ID;
    }

}
