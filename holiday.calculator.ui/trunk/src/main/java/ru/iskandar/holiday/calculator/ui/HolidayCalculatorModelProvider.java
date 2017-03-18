package ru.iskandar.holiday.calculator.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.dataconnection.ClientConnector;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;

public class HolidayCalculatorModelProvider implements ILoadingProvider {

	/** Задача загрузки модели */
	private FutureTask<HolidayCalculatorModel> _task = new LoadModelTask(new LoadModelCallable());

	/** Слушатели изменения статуса загрузки */
	private final CopyOnWriteArrayList<ILoadListener> _loadListeners = new CopyOnWriteArrayList<>();

	/**
	 * Конструктор
	 */
	HolidayCalculatorModelProvider() {
		Executors.newSingleThreadExecutor().submit(_task);
	}

	/**
	 * Возвращает модель
	 *
	 * @return модель
	 * @throws IllegalStateException
	 *             если не удалось загрузить модель
	 * @see #getLoadStatus()
	 */
	public HolidayCalculatorModel getModel() {
		try {
			return _task.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException("Ошибка получения модели учета отгулов", e);
		}
	}

	/**
	 * Загрузчик модели
	 */
	private class LoadModelCallable implements Callable<HolidayCalculatorModel> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public HolidayCalculatorModel call() throws Exception {
			return new ClientConnector().loadModel();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LoadStatus getLoadStatus() {
		if (!_task.isDone() && !_task.isCancelled()) {
			return LoadStatus.LOADING;
		}
		try {
			_task.get();
		} catch (InterruptedException | ExecutionException e) {
			return LoadStatus.LOAD_ERROR;
		}
		return LoadStatus.LOADED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLoadListener(ILoadListener aLoadListener) {
		_loadListeners.add(aLoadListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLoadListener(ILoadListener aLoadListener) {
		_loadListeners.remove(aLoadListener);
	}

	/**
	 * Оповещает слушателей об изменении статуса загрузки
	 */
	private void fireLoadStatusChangedEvent() {
		for (ILoadListener listener : _loadListeners) {
			listener.loadStatusChanged();
		}
	}

	/**
	 * Асинхронно перезагружает модель
	 */
	public synchronized void asynReload() {
		_task = new LoadModelTask(new LoadModelCallable());
		Executors.newSingleThreadExecutor().submit(_task);
		fireLoadStatusChangedEvent();
	}

	/**
	 * Задача загрузки модели
	 */
	private class LoadModelTask extends FutureTask<HolidayCalculatorModel> {

		/**
		 * Конструктор
		 */
		public LoadModelTask(Callable<HolidayCalculatorModel> aCallable) {
			super(aCallable);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void done() {
			try {
				get();
			} catch (InterruptedException | ExecutionException e) {
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.modelLoadError, e), StatusManager.LOG);
			}

			try {
				fireLoadStatusChangedEvent();
			} catch (Exception e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						Messages.modelLoadStatusChangedNotificationError, e), StatusManager.LOG);
			}
		}

	}

}
