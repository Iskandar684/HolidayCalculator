package ru.iskandar.holiday.calculator.ui.user;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;

/**
 *
 */
public class UsersTableContentProvider implements IStructuredContentProvider {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(Object aInputElement) {
		HolidayCalculatorModelProvider modelProvider = (HolidayCalculatorModelProvider) aInputElement;

		LoadStatus loadStatus = modelProvider.getLoadStatus();
		if (!LoadStatus.LOADED.equals(loadStatus)) {
			return EMPTY_ARRAY;
		}

		HolidayCalculatorModel model = modelProvider.getModel();
		if (!model.canViewUsers()) {
			return EMPTY_ARRAY;
		}

		return model.getAllUsers().toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
	}

}
