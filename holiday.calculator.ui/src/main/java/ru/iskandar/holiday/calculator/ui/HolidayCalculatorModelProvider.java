package ru.iskandar.holiday.calculator.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.dataconnection.ClientConnector;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;

/**
 * Поставщик модели отгулов
 */
public class HolidayCalculatorModelProvider extends ModelProvider<HolidayCalculatorModel> implements ILoadingProvider {

	/** Слушатели модели */
	private final CopyOnWriteArrayList<IHolidayCalculatorModelListener> _modelListeners = new CopyOnWriteArrayList<>();

	/** Внутренний слушатель модели */
	private final InternalHolidayCalculatorModelListener _internalModelListener = new InternalHolidayCalculatorModelListener();

	/**
	 * Конструктор
	 */
	HolidayCalculatorModelProvider() {
		super(new LoadModelCallable());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayCalculatorModel getModel() {
		return super.getModel();
	}

	/**
	 * Загрузчик модели
	 */
	private static class LoadModelCallable implements Callable<HolidayCalculatorModel> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public HolidayCalculatorModel call() throws Exception {
			return new ClientConnector().loadModel();
		}

	}

	/**
	 * Добавляет слушателя
	 *
	 * @param aListener
	 *            слушатель
	 */
	public void addListener(IHolidayCalculatorModelListener aListener) {
		_modelListeners.add(aListener);
	}

	/**
	 * Удаляет слушателя
	 *
	 * @param aListener
	 *            слушатель
	 */
	public void removeListener(IHolidayCalculatorModelListener aListener) {
		_modelListeners.remove(aListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void internalModelSwitched(HolidayCalculatorModel aOldModel, HolidayCalculatorModel aNewModel) {
		if (aOldModel != null) {
			aOldModel.removeListener(_internalModelListener);
		}
		if (aNewModel != null) {
			aNewModel.addListener(_internalModelListener);
		}
	}

	/**
	 * Внутренний слушатель модели
	 */
	private class InternalHolidayCalculatorModelListener implements IHolidayCalculatorModelListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handleEvent(HolidayCalculatorEvent aEvent) {
			for (IHolidayCalculatorModelListener listener : _modelListeners) {
				try {
					listener.handleEvent(aEvent);
				} catch (Exception e) {
					StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							String.format("Ошибка перессылки события", aEvent), e), StatusManager.LOG);
				}
			}

		}

	}

}
