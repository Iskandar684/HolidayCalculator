package ru.iskandar.holiday.calculator.branding;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.ui.NavigationViewPart;

/** Главная перспектива */
public class MainPerspective implements IPerspectiveFactory {

	/** Идентификатор перспективы */
	public static final String ID = "ru.iskandar.holiday.calculator.branding.MainPerspective";

	/** {@inheritDoc} */
	@Override
	public void createInitialLayout(IPageLayout aLayout) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		shell.setMaximized(true);
		aLayout.setEditorAreaVisible(true);
		aLayout.addPerspectiveShortcut(ID);
		aLayout.setFixed(true);

		final String contentsID = NavigationViewPart.ID;
		final float r = 0.27f;
		aLayout.addStandaloneView(contentsID, false, IPageLayout.LEFT, r, aLayout.getEditorArea());
		final IViewLayout contentsLayout = aLayout.getViewLayout(contentsID);
		if (contentsLayout != null) {
			contentsLayout.setCloseable(false);
			contentsLayout.setMoveable(false);
		}
		aLayout.addShowViewShortcut(contentsID);

	}

}
