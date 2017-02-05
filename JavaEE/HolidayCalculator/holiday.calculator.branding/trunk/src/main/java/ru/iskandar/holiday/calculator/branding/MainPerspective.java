package ru.iskandar.holiday.calculator.branding;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/** Главная перспектива */
public class MainPerspective implements IPerspectiveFactory {

	/** Идентификатор перспективы */
	public static final String ID = "ru.iskandar.holiday.calculator.branding.MainPerspective";

	/** {@inheritDoc} */
	@Override
	public void createInitialLayout(IPageLayout aLayout) {
		// aLayout.setEditorAreaVisible(true);
		// aLayout.addPerspectiveShortcut(ID);
		// final String contentsID = NavigationViewPart.ID;
		// final float r = 0.27f;
		// aLayout.addView(contentsID, IPageLayout.LEFT, r,
		// aLayout.getEditorArea());
		// final IViewLayout contentsLayout = aLayout.getViewLayout(contentsID);
		// if (contentsLayout != null) {
		// contentsLayout.setCloseable(false);
		// contentsLayout.setMoveable(false);
		// }
		// aLayout.addShowViewShortcut(contentsID);
	}

}
