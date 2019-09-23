package ru.iskandar.holiday.calculator.ui.statement;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.statement.IStatementsProvider.IStatementsChangedListener;

/**
 * Создаватель таблицы просмотра заявлений
 */
public class StatementsTableCreator {

	private GridTableViewer _viewer;

	public static enum StatementsTableColumn {

		TYPE(StatementsTableProperties.statementsTableTypeColumnText, 0, false),

		AUTHOR(StatementsTableProperties.statementsTableAuthorColumnText, 1, false),

		CREATE_DATE(StatementsTableProperties.statementsTableCreateDateColumnText, 2, false),

		STATUS(StatementsTableProperties.statementsTableStatusColumnText, 3, false),

		CONSIDER(StatementsTableProperties.statementsTableConsiderColumnText, 4, false),

		CONSIDER_DATE(StatementsTableProperties.statementsTableConsiderDateColumnText, 5, false),

		CONTENT(StatementsTableProperties.statementsTableContentColumnText, 6, true);

		private String _text;

		private int _index;

		private boolean _wordWrap;

		StatementsTableColumn(String aText, int aIndex, boolean aWordWrap) {
			_text = aText;
			_index = aIndex;
			_wordWrap = aWordWrap;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return _text;
		}

		/**
		 * @return the index
		 */
		public int getIndex() {
			return _index;
		}

		public boolean isWordWrap() {
			return _wordWrap;
		}

		public static StatementsTableColumn findColumnByIndex(int aIndex) {
			for (StatementsTableColumn col : StatementsTableColumn.values()) {
				if (col.getIndex() == aIndex) {
					return col;
				}
			}
			return null;
		}

	}

	private final IStatementsProvider _modelProvider;

	public StatementsTableCreator(IStatementsProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_modelProvider = aHolidayCalculatorModelProvider;
	}

	public StructuredViewer create(Composite aParent) {
		_viewer = new GridTableViewer(aParent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		_viewer.getGrid().setAutoHeight(true);
		_viewer.getGrid().setAutoWidth(true);
		for (StatementsTableColumn col : StatementsTableColumn.values()) {
			GridColumn column = new GridColumn(_viewer.getGrid(), SWT.LEFT);
			column.setText(col.getText());
			column.setMoveable(true);
			column.setWordWrap(col.isWordWrap());
		}
		_viewer.getGrid().setHeaderVisible(true);
		_viewer.getGrid().setLinesVisible(true);

		initListeners();
		_viewer.setContentProvider(new StatementTableContentProvider());
		_viewer.setLabelProvider(new StatementsTableLabelProvider());
		_viewer.setInput(_modelProvider);

		_viewer.getGrid().addPaintListener(new PaintHandler());
		updateLinesVisible();

		refreshAndPack();
		return _viewer;
	}

	public void showStatement(StatementId aStatementId) {
		Optional<Statement<?>> statementOpt = _modelProvider.getStatements().stream()
				.filter(statement -> statement.getId().equals(aStatementId)).findAny();
		statementOpt.ifPresent(statement -> _viewer.setSelection(new StructuredSelection(statement), true));
	}

	private void updateLinesVisible() {
		boolean visible = LoadStatus.LOADED.equals(_modelProvider.getLoadStatus());
		_viewer.getGrid().setLinesVisible(visible);
	}

	private class PaintHandler implements PaintListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paintControl(PaintEvent aE) {
			LoadStatus status = _modelProvider.getLoadStatus();
			String text = Messages.EMPTY;
			switch (status) {
			case LOADING:
				text = StatementsTableProperties.statementsTableLoadingText;
				break;

			case LOAD_ERROR:
				text = StatementsTableProperties.statementsTableLoadErrorText;
				break;

			case LOADED:
				text = Messages.EMPTY;
				break;

			default:
				break;
			}

			int x = (aE.width - aE.gc.textExtent(text).x) / 2;
			int y = (aE.height + aE.gc.textExtent(text).y) / 2;

			aE.gc.drawText(text, x, y);

		}

	}

	private void initListeners() {
		ILoadListener loadListener = () -> Display.getDefault().asyncExec(() -> {
			updateLinesVisible();
			refreshAndPack();
		});
		IStatementsChangedListener modelListener = () -> Display.getDefault().asyncExec(() -> refreshAndPack());
		_modelProvider.addStatementsChangedListener(modelListener);
		_modelProvider.addLoadListener(loadListener);
		_viewer.getControl().addDisposeListener(aE -> {
			_modelProvider.removeLoadListener(loadListener);
			_modelProvider.removeStatementsChangedListener(modelListener);
		});
	}

	private void refreshAndPack() {
		_viewer.refresh();
		for (GridColumn col : _viewer.getGrid().getColumns()) {
			if (col.getWordWrap()) {
				col.setWidth(200);
			} else {
				col.pack();
			}
		}
	}

}
