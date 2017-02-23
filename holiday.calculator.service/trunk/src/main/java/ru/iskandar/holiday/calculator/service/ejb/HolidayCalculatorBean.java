package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelFactory;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelException {
		return new HolidayCalculatorModelFactory().create();
	}

}