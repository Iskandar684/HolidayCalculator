package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;

/**
 * @author Искандар
 *
 */
public class SearchResultTableContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object aInputElement) {
		return ((ISearchResult) aInputElement).getHits().toArray();
	}
}
