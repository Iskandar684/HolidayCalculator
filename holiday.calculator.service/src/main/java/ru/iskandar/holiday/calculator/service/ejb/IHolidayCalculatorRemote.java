package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorService;

/**
 * Удаленный сервис учета отгулов
 */
public interface IHolidayCalculatorRemote extends IHolidayCalculatorService {

	/** JNDI имя */
	public static String JNDI_NAME = "holiday/holiday.calculator.service/HolidayCalculatorBean!ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote";

}