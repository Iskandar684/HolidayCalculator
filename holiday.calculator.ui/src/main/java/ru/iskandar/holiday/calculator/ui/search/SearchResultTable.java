package ru.iskandar.holiday.calculator.ui.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
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

import ru.iskandar.holiday.calculator.service.model.search.ISearchHit;
import ru.iskandar.holiday.calculator.service.model.search.ISearchResult;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.grid.StyledTextCellRenderer;

/**
 * Таблица с результатами поиска.
 */
public class SearchResultTable {

	private GridTableViewer _viewer;

	private ISearchResult _searchResult;

	/** Зрители результата поиска */
	private final List<ISearchHitViewer> _searchHitViewers = new ArrayList<>();

	public GridTableViewer create(Composite aParent) {
		_viewer = new GridTableViewer(aParent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		GridColumn column = new GridColumn(_viewer.getGrid(), SWT.LEFT | SWT.WRAP);
		column.setWordWrap(true);
		StyledTextCellRenderer cellRenderer = new StyledTextCellRenderer(_viewer);
		cellRenderer.setWordWrap(true);
		column.setCellRenderer(cellRenderer);
		_viewer.getGrid().setHeaderVisible(false);
		_viewer.getGrid().setLinesVisible(false);
		_viewer.getGrid().setAutoHeight(true);
		_viewer.setContentProvider(new SearchResultTableContentProvider());
		_viewer.setLabelProvider(new SearchResultTableLabelProvider(() -> getHighlightedText()));
		_viewer.getGrid().addPaintListener(new PaintHandler());
		fillColumn();
		_viewer.getGrid().addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent aE) {
				fillColumn();
			}
		});
		_viewer.addOpenListener(this::open);
		return _viewer;
	}

	private void open(OpenEvent aEvent) {
		Optional<ISearchHit> hit = getSearchHit(aEvent);
		hit.ifPresent(this::open);
	}

	private void open(ISearchHit aHit) {
		for (ISearchHitViewer viewer : _searchHitViewers) {
			if (viewer.canView(aHit)) {
				viewer.view(aHit);
			}
		}
	}

	private Optional<ISearchHit> getSearchHit(OpenEvent aEvent) {
		IStructuredSelection sel = (IStructuredSelection) aEvent.getSelection();
		Object firstElement = sel.getFirstElement();
		if (firstElement instanceof ISearchHit) {
			ISearchHit hit = ((ISearchHit) firstElement);
			return Optional.of(hit);
		}
		return Optional.empty();
	}

	private String getHighlightedText() {
		if (_searchResult != null) {
			return _searchResult.getSearchString();
		}
		return Util.ZERO_LENGTH_STRING;
	}

	public void setInput(ISearchResult aSearchResult) {
		_searchResult = aSearchResult;
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

	public void addSearchHitViewer(ISearchHitViewer aViewer) {
		_searchHitViewers.add(aViewer);
	}

}
