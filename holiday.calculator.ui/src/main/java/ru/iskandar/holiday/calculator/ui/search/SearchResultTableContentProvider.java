package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ru.iskandar.holiday.calculator.service.model.search.ISearchResult;

/**
 * Поставщик результатов поиска.
 */
public class SearchResultTableContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object aInputElement) {
		return ((ISearchResult) aInputElement).getHits().toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
	}

}
