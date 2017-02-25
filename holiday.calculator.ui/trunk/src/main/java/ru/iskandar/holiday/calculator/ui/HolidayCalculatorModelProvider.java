package ru.iskandar.holiday.calculator.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.ui.dataconnection.ClientConnector;

public class HolidayCalculatorModelProvider implements ILoadingProvider {

	/** Задача загрузки модели */
	private final FutureTask<HolidayCalculatorModel> _task = new FutureTask<>(new LoadModelCallable());

	/**
	 * Конструктор
	 */
	public HolidayCalculatorModelProvider() {
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

}
