package ru.iskandar.holiday.calculator.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import ru.iskandar.holiday.calculator.clientlibraries.ClientConnector;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;

public class HolidayCalculatorModelProvider {

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

}
