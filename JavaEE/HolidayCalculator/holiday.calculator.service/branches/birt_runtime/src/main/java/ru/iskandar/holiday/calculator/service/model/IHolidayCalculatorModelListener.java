package ru.iskandar.holiday.calculator.service.model;

/**
 * Обработчик события
 */
public interface IHolidayCalculatorModelListener {

	/**
	 * Обрабатывает событие
	 *
	 * @param aEvent
	 *            событие
	 */
	public void handleEvent(HolidayCalculatorEvent aEvent);

}
