package ru.iskandar.holiday.calculator.service.model;

/**
 * Фабрика создания модели учета отгулов
 */
public class HolidayCalculatorModelFactory {

	/**
	 * Создает модель
	 * 
	 * @return модель
	 */
	public HolidayCalculatorModel create() {
		return new HolidayCalculatorModel();
	}

}
