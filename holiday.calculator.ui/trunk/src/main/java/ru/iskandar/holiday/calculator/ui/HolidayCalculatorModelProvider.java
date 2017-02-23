package ru.iskandar.holiday.calculator.ui;

import ru.iskandar.holiday.calculator.clientlibraries.ClientConnector;
import ru.iskandar.holiday.calculator.clientlibraries.ConnectionException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;

public class HolidayCalculatorModelProvider {

	private HolidayCalculatorModel _model;

	public synchronized HolidayCalculatorModel getModel() {
		// TODO переделать на FutureTask
		if (_model == null) {
			try {
				_model = new ClientConnector().loadModel();
			} catch (ConnectionException e) {
				throw new IllegalStateException("Ошибка загрузки модели учета отгулов", e);
			}
		}
		return _model;
	}

}
