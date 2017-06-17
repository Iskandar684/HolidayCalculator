package ru.iskandar.holiday.calculator.ui;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

public class ModelProvider<M> implements ILoadingProvider {

	/** Задача загрузки модели */
	private FutureTask<M> _task;

	/** Слушатели изменения статуса загрузки */
	private final CopyOnWriteArrayList<ILoadListener> _loadListeners = new CopyOnWriteArrayList<>();

	/** Загрузчик модели */
	private final Callable<M> _loader;

	/**
	 * Конструктор
	 */
	protected ModelProvider(Callable<M> aLoader) {
		Objects.requireNonNull(aLoader);
		_loader = aLoader;
		load();
	}

	private void load() {
		_task = new LoadModelTask(_task, _loader);
		Executors.newSingleThreadExecutor().submit(_task);
		fireLoadStatusChangedEvent();
	}

	/**
	 * Возвращает модель
	 *
	 * @return модель
	 * @throws IllegalStateException
	 *             если не удалось загрузить модель
	 * @see #getLoadStatus()
	 */
	protected M getModel() {
		try {
			return _task.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException("Ошибка получения модели учета отгулов", e);
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
			try {
				listener.loadStatusChanged();
			} catch (Exception e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						Messages.modelLoadStatusChangedNotificationError, e), StatusManager.LOG);
			}

		}
	}

	/**
	 * Асинхронно перезагружает модель
	 */
	public synchronized void asynReload() {
		load();
	}

	/**
	 * Задача загрузки модели
	 */
	private class LoadModelTask extends FutureTask<M> {

		/** Предыдущая задача загрузки */
		private final FutureTask<M> _previousTask;

		/**
		 * Конструктор
		 *
		 * @param aPreviousTask
		 *            предыдущая задача загрузки
		 */
		public LoadModelTask(FutureTask<M> aPreviousTask, Callable<M> aCallable) {
			super(aCallable);
			_previousTask = aPreviousTask;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void done() {
			M newModel;
			try {
				newModel = get();
			} catch (InterruptedException | ExecutionException e) {
				newModel = null;
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.modelLoadError, e), StatusManager.LOG);
			}
			M oldModel;
			try {
				oldModel = _previousTask != null ? _previousTask.get() : null;
			} catch (InterruptedException | ExecutionException e) {
				// логгировать не нужно, т.к. логгировали уже ранее
				oldModel = null;
			}
			internalModelSwitched(oldModel, newModel);
			fireLoadStatusChangedEvent();
		}

	}

	/**
	 * Обрабатывает смену модели
	 *
	 * @param aOldModel
	 *            старая модель
	 * @param aNewModel
	 *            новая модель
	 */
	protected void internalModelSwitched(M aOldModel, M aNewModel) {
	}

}
