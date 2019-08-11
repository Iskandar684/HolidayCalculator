package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Таблица с результатами поиска.
 */
public class SearchResultTable {

	private GridTableViewer _viewer;

	public GridTableViewer create(Composite aParent) {
		_viewer = new GridTableViewer(aParent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		GridColumn column = new GridColumn(_viewer.getGrid(), SWT.LEFT | SWT.WRAP);
		column.setWordWrap(true);
		column.getCellRenderer().setWordWrap(true);
		_viewer.getGrid().setHeaderVisible(false);
		_viewer.getGrid().setLinesVisible(false);
		_viewer.getGrid().setAutoHeight(true);
		_viewer.setContentProvider(new SearchResultTableContentProvider());
		_viewer.setLabelProvider(new SearchResultTableLabelProvider());
		_viewer.getGrid().addPaintListener(new PaintHandler());
		fillColumn();
		_viewer.getGrid().addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent aE) {
				fillColumn();
			}
		});
		return _viewer;
	}

	public void setInput(ISearchResult aSearchResult) {
		_viewer.setInput(aSearchResult);
		_viewer.refresh();
		fillColumn();
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

	private void fillColumn() {
		Rectangle area = _viewer.getGrid().getClientArea();
		GridColumn column = _viewer.getGrid().getColumn(0);
		column.setWidth(area.width);
	}

	public Control getControl() {
		return _viewer.getControl();
	}

}
