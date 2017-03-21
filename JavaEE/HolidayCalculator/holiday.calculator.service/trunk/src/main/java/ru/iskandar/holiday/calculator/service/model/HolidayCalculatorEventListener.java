/**
 *
 */
package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author Windows 7 x64
 *
 */
public class HolidayCalculatorEventListener implements MessageListener {
	/***/
	private final HolidayCalculatorModel _model;

	/**
	 *
	 */
	public HolidayCalculatorEventListener(HolidayCalculatorModel aModel) {
		Objects.requireNonNull(aModel);
		_model = aModel;
	}

	@Override
	public void onMessage(Message aMessage) {
		// TODO
		System.out.println("onEvent in model " + aMessage);
		HolidayCalculatorEvent event;
		try {
			event = (HolidayCalculatorEvent) ((ObjectMessage) aMessage).getObject();
		} catch (JMSException e) {
			throw new IllegalStateException("Ошибка получения содержимого сообщения", e);
		}

		if (event instanceof HolidayStatementSendedEvent) {
			_model.fireHolidayStatementSended((HolidayStatementSendedEvent) event);
		}
	}

}
