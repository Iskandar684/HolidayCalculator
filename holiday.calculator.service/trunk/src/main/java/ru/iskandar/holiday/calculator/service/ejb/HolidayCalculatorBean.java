package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelException;

@Stateless
@Remote(IHolidayCalculatorRemote.class)
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {

	@Override
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelException {
		return new HolidayCalculatorModel();
	}

}