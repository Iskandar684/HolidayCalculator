package ru.iskandar.holiday.calculator.ui.makerecall;

import java.util.Objects;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.takeholiday.TakeHolidayEditor;

/**
 * Контроллер кнопки формирования заявления на отзыв
 *
 */
public class MakeRecallButtonPM {

	/** Кнопка */
	private final Button _button;

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _provider;

	/**
	 * Конструктор
	 */
	public MakeRecallButtonPM(Button aButton, HolidayCalculatorModelProvider aProvider) {
		Objects.requireNonNull(aButton);
		Objects.requireNonNull(aProvider);
		_button = aButton;
		_provider = aProvider;
		aButton.addSelectionListener(new BtSelectionListener());

		final LoadListener loadListener = new LoadListener();
		_provider.addLoadListener(loadListener);
		final ModelListener modelListener = new ModelListener();
		_provider.addListener(modelListener);

		aButton.addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_provider.removeLoadListener(loadListener);
				_provider.removeListener(modelListener);
			}
		});
		update();
	}

	private class ModelListener implements IHolidayCalculatorModelListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handleEvent(HolidayCalculatorEvent aEvent) {
			Display.getDefault().asyncExec(new Runnable() {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void run() {
					update();
				}

			});

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
					update();
				}

			});

		}

	}

	/**
	 * Обновляет кнопку
	 */
	private void update() {
		boolean enabled = false;
		LoadStatus loadStatus = _provider.getLoadStatus();
		if (LoadStatus.LOADED.equals(loadStatus)) {
			enabled = _provider.getModel().canCreateMakeRecallStatement();
		}
		_button.setEnabled(enabled);
	}

	/**
	 * Обработчик нажатия кнопки
	 */
	private class BtSelectionListener extends SelectionAdapter {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetSelected(SelectionEvent aE) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
						new MakeRecallEditorInput(_provider), MakeRecallEditor.EDITOR_ID, true,
						IWorkbenchPage.MATCH_ID);

			} catch (PartInitException e) {
				throw new IllegalStateException(
						String.format("Ошибка открытия редактора подачи заявления на отпуск [viewId=%s]",
								TakeHolidayEditor.EDITOR_ID),
						e);
			}
		}

	}

}