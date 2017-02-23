package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelException;

public interface IHolidayCalculatorRemote {

	/** JNDI имя */
	public static String JNDI_NAME = "holiday.calculator.service/HolidayCalculatorBean!ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote";

	/**
	 * Загружает модель учета отгулов для текущего пользователя
	 *
	 * @return модель
	 * @throws HolidayCalculatorModelException
	 *             ошибка загрузки модели
	 */
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelException;
}