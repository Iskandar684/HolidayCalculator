package ru.iskandar.holiday.calculator.service.event;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;

/**
 * Отправитель события сервиса учета отгулов.
 *
 */
public interface IHolidayCalculatorEventSender {

	/**
	 * Отправляет событие.
	 *
	 * @param aEvent событие
	 * @throws HolidayCalculatorException в случае ошибки отправки события
	 */
	void send(HolidayCalculatorEvent aEvent) throws HolidayCalculatorException;

}
