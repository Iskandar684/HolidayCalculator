package ru.iskandar.holiday.calculator.ui.incoming;

import java.util.Objects;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
  *
 */
public class StatementsTableCreator {

	private TableViewer _viewer;

	public static enum StatementsTableColumn {

		TYPE(Messages.statementsTableTypeColumnText, 0),

		AUTHOR(Messages.statementsTableAuthorColumnText, 1),

		CREATE_DATE(Messages.statementsTableCreateDateColumnText, 2),

		STATUS(Messages.statementsTableStatusColumnText, 3);

		private String _text;

		private int _index;

		StatementsTableColumn(String aText, int aIndex) {
			_text = aText;
			_index = aIndex;
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

		public static StatementsTableColumn findColumnByIndex(int aIndex) {
			for (StatementsTableColumn col : StatementsTableColumn.values()) {
				if (col.getIndex() == aIndex) {
					return col;
				}
			}
			return null;
		}

	}

	private final HolidayCalculatorModelProvider _modelProvider;

	public StatementsTableCreator(HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_modelProvider = aHolidayCalculatorModelProvider;
	}

	public TableViewer create(Composite aParent) {
		_viewer = new TableViewer(aParent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);

		for (StatementsTableColumn col : StatementsTableColumn.values()) {
			TableColumn column = new TableColumn(_viewer.getTable(), SWT.CENTER);
			column.setText(col.getText());
		}
		_viewer.getTable().setHeaderVisible(true);
		_viewer.getTable().setLinesVisible(true);

		initListeners();
		_viewer.setContentProvider(new IncomingTableContentProvider());
		_viewer.setLabelProvider(new StatementsTableLabelProvider());
		_viewer.setInput(_modelProvider);
		refreshAndPack();
		return _viewer;
	}

	private void initListeners() {
		ILoadListener loadListener = new ILoadListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void loadStatusChanged() {
				Display.getDefault().asyncExec(new Runnable() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						refreshAndPack();
					}

				});

			}

		};
		IHolidayCalculatorModelListener modelListener = new IHolidayCalculatorModelListener() {

			/**
			 * @param aAEvent
			 */
			@Override
			public void handleEvent(HolidayCalculatorEvent aAEvent) {
				Display.getDefault().asyncExec(new Runnable() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						refreshAndPack();
					}

				});

			}
		};
		_modelProvider.addListener(modelListener);
		_modelProvider.addLoadListener(loadListener);
		_viewer.getControl().addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_modelProvider.removeLoadListener(loadListener);
				_modelProvider.removeListener(modelListener);
			}
		});
	}

	private void refreshAndPack() {
		_viewer.refresh();
		for (TableColumn col : _viewer.getTable().getColumns()) {
			col.pack();
		}
	}

}
