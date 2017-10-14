package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorService;

/**
 * Локальный сервис учета отгулов
 */
public interface IHolidayCalculatorLocal extends IHolidayCalculatorService {

	/** JNDI имя */
	public static String JNDI_NAME = "java:app/holiday-calculator-web-service/HolidayCalculatorBean!ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal";

}