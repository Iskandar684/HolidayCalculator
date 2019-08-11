package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableColumn;

import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Таблица с результатами поиска.
 */
public class SearchResultTable {

	private TableViewer _viewer;

	public TableViewer create(Composite aParent) {
		_viewer = new TableViewer(aParent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		TableColumn column = new TableColumn(_viewer.getTable(), SWT.CENTER);
		column.setMoveable(true);
		_viewer.getTable().setHeaderVisible(true);
		_viewer.getTable().setLinesVisible(true);
		_viewer.setContentProvider(new SearchResultTableContentProvider());
		_viewer.setLabelProvider(new SearchResultTableLabelProvider());
		_viewer.getTable().addPaintListener(new PaintHandler());
		updateLinesVisible();
		return _viewer;
	}

	public void setInput(ISearchResult aSearchResult) {
		_viewer.setInput(aSearchResult);
		refreshAndPack();
	}

	private void updateLinesVisible() {
		_viewer.getTable().setLinesVisible(hasResult());
	}

	private boolean hasResult() {
		Object input = _viewer.getInput();
		if (input instanceof ISearchResult) {
			return !((ISearchResult) input).getHits().isEmpty();
		}
		return false;
	}

	private class PaintHandler implements PaintListener {

		@Override
		public void paintControl(PaintEvent aE) {
			String text = hasResult() ? Messages.EMPTY : "Ничего не найдено.";
			int x = (aE.width - aE.gc.textExtent(text).x) / 2;
			int y = (aE.height + aE.gc.textExtent(text).y) / 2;
			aE.gc.drawText(text, x, y);
		}

	}

	private void refreshAndPack() {
		_viewer.refresh();
		for (TableColumn col : _viewer.getTable().getColumns()) {
			col.pack();
		}
	}

	public Control getControl() {
		return _viewer.getControl();
	}

}
