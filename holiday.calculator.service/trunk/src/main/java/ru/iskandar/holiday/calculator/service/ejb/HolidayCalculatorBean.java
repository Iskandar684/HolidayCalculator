package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;

import ru.iskandar.holiday.calculator.service.ejb.jms.MessageSenderBean;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
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
	private IUserServiceLocal _currentUserServiceLocal;

	/** Отправитель сообщения */
	@EJB
	private MessageSenderBean _messageSender;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelLoadException {
		HolidayCalculatorEvent event = new HolidayCalculatorEvent();
		try {
			// TODO временно, для отладки
			_messageSender.send(event);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HolidayCalculatorModelLoadException("Ошибка отправки сообщения", e);
		}
		return new HolidayCalculatorModelFactory(_currentUserServiceLocal).create();
	}

}