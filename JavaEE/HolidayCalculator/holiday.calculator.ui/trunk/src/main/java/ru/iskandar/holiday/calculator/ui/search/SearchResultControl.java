package ru.iskandar.holiday.calculator.ui.search;

import java.math.BigDecimal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import ru.iskandar.holiday.calculator.service.model.search.ISearchResult;
import ru.iskandar.holiday.calculator.ui.user.UserSearchHitViewer;

/**
 * Элемент управления просмотра результатов поиска.
 */
public class SearchResultControl {

	/** Таблица с результатами поиска */
	private SearchResultTable _searchResultTable;

	/** Корневой элемент управления */
	private Composite _control;

	/** Строка отображения общей информации о результатов поиска */
	private Label _searchResultSummaryLb;

	public Control create(Composite aParent) {
		_control = new Composite(aParent, SWT.NONE);
		_control.setLayout(new GridLayout());
		_searchResultSummaryLb = new Label(_control, SWT.NONE);
		_searchResultSummaryLb.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		_searchResultTable = new SearchResultTable();
		_searchResultTable.create(_control);
		_searchResultTable.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_searchResultTable.addSearchHitViewer(new UserSearchHitViewer());
		return _control;
	}

	public void setInput(ISearchResult aSearchResult) {
		BigDecimal took = new BigDecimal(aSearchResult.getTookInMillis());
		took.setScale(4);
		took = took.divide(BigDecimal.valueOf(1000));
		String info = String.format("Результатов: %s (%s сек.)", aSearchResult.getTotalHitsCount(), took);
		_searchResultSummaryLb.setText(info);
		_searchResultTable.setInput(aSearchResult);
	}

	public Control getControl() {
		return _control;
	}

}
