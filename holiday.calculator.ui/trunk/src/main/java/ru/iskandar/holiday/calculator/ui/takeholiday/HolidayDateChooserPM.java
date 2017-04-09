package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.eclipse.nebula.widgets.datechooser.DateChooser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;

import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;

/**
 *
 */
public class HolidayDateChooserPM {

	private final DateChooser _dateTime;

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public HolidayDateChooserPM(DateChooser aDateTime, HolidayCalculatorModelProvider aModelProvider) {
		Objects.requireNonNull(aDateTime);
		Objects.requireNonNull(aModelProvider);
		_dateTime = aDateTime;
		_modelProvider = aModelProvider;
		final ILoadListener loadListener = new LoadListener();
		_modelProvider.addLoadListener(loadListener);
		aDateTime.addDisposeListener(new DisposeListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_modelProvider.removeLoadListener(loadListener);
			}

		});

		aDateTime.addSelectionListener(new SelectionHandler());

		update();
	}

	private class SelectionHandler extends SelectionAdapter {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetSelected(SelectionEvent aE) {
			@SuppressWarnings("unchecked")
			Collection<Date> selection = _dateTime.getSelectedDates();
			_modelProvider.getModel().getHolidayStatementBuilder().setDates(selection);
		}
	}

	private class LoadListener implements ILoadListener {
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
					HolidayDateChooserPM.this.update();
				}

			});

		}

	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		boolean enabled;
		if (LoadStatus.LOADED.equals(_modelProvider.getLoadStatus())) {
			enabled = true;
			TakeHolidayStatementBuilder builder = _modelProvider.getModel().getHolidayStatementBuilder();
			for (Date date : builder.getDays()) {
				_dateTime.setSelectedDate(date);
			}
		} else {
			enabled = false;
		}
		_dateTime.setEnabled(enabled);
	}
}
