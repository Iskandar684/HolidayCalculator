package ru.iskandar.holiday.calculator.ui.user;

import java.util.Objects;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorListenerAdapter;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.service.model.UserCreatedEvent;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Создаватель таблицы пользователей
 */
public class UsersTableCreator {

	private GridTableViewer _viewer;

	public static enum UsersTableColumn {

		FIRST_NAME(UsersTableProperties.firstNameLabel, 0),

		LAST_NAME(UsersTableProperties.lastNameLabel, 1),

		PATRONYMIC(UsersTableProperties.patronymicLabel, 2),

		LOGIN(UsersTableProperties.loginLabel, 3),

		EMPLOYMENT(UsersTableProperties.employmentDate, 4),

		NOTE(UsersTableProperties.note, 5);

		private String _text;

		private int _index;

		UsersTableColumn(String aText, int aIndex) {
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

		public static UsersTableColumn findColumnByIndex(int aIndex) {
			for (UsersTableColumn col : UsersTableColumn.values()) {
				if (col.getIndex() == aIndex) {
					return col;
				}
			}
			return null;
		}

	}

	private final HolidayCalculatorModelProvider _modelProvider;

	public UsersTableCreator(HolidayCalculatorModelProvider aHolidayCalculatorModelProvider) {
		Objects.requireNonNull(aHolidayCalculatorModelProvider);
		_modelProvider = aHolidayCalculatorModelProvider;
	}

	public StructuredViewer create(Composite aParent) {
		_viewer = new GridTableViewer(aParent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);

		for (UsersTableColumn col : UsersTableColumn.values()) {
			GridColumn column = new GridColumn(_viewer.getGrid(), SWT.CENTER);
			column.setText(col.getText());
			column.setMoveable(true);
			if (UsersTableColumn.NOTE == col) {
				column.setWordWrap(true);
			}
		}
		_viewer.getGrid().setHeaderVisible(true);
		_viewer.getGrid().setLinesVisible(true);
		_viewer.getGrid().setAutoHeight(true);
		_viewer.getGrid().setAutoWidth(true);

		initListeners();
		_viewer.setContentProvider(new UsersTableContentProvider());
		_viewer.setLabelProvider(new UsersTableLabelProvider());
		_viewer.setInput(_modelProvider);

		_viewer.getGrid().addPaintListener(new PaintHandler());
		updateLinesVisible();

		refreshAndPack();
		return _viewer;
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
				text = UsersTableProperties.usersTableLoadingText;
				break;

			case LOAD_ERROR:
				text = UsersTableProperties.usersTableLoadErrorText;
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
						updateLinesVisible();
						refreshAndPack();
					}

				});

			}

		};
		IHolidayCalculatorModelListener modelListener = new HolidayCalculatorListenerAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected void userCreated(UserCreatedEvent aEvent) {
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
		for (GridColumn col : _viewer.getGrid().getColumns()) {
			col.pack();
		}
	}

}
