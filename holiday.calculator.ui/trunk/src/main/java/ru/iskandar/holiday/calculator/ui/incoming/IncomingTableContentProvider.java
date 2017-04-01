package ru.iskandar.holiday.calculator.ui.incoming;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;

/**
 *
 */
public class IncomingTableContentProvider implements IStructuredContentProvider {

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
		if (!model.canConsider()) {
			return EMPTY_ARRAY;
		}
		Collection<Statement> statements = model.getIncomingStatements();
		return statements.toArray();
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
