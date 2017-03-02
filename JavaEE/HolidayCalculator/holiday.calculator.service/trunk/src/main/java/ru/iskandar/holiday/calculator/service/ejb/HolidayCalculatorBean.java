package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelFactory;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {

	/** Сервис работы с текущим пользователем */
	@EJB
	private ICurrentUserServiceLocal _currentUserServiceLocal;

	/**
	 * {@inheritDoc}
	 */

	@Override
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelLoadException {
		return new HolidayCalculatorModelFactory(_currentUserServiceLocal).create();
	}

}