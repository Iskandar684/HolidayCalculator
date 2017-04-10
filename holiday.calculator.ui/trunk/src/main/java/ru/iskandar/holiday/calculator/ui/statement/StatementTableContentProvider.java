package ru.iskandar.holiday.calculator.ui.statement;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;

/**
 *
 */
public class StatementTableContentProvider implements IStructuredContentProvider {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(Object aInputElement) {
		IStatementsProvider modelProvider = (IStatementsProvider) aInputElement;

		LoadStatus loadStatus = modelProvider.getLoadStatus();
		if (!LoadStatus.LOADED.equals(loadStatus)) {
			return EMPTY_ARRAY;
		}

		Collection<Statement> statements = modelProvider.getStatements();
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
