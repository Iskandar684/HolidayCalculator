package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Слушатетель JMS
 */
public class HolidayCalculatorEventListener implements MessageListener {

	/** Модель учета отгулов */
	private final HolidayCalculatorModel _model;

	/**
	 * Конструктор
	 * 
	 * @param aModel
	 *            модель учета отгулов
	 */
	public HolidayCalculatorEventListener(HolidayCalculatorModel aModel) {
		Objects.requireNonNull(aModel);
		_model = aModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMessage(Message aMessage) {
		HolidayCalculatorEvent event;
		try {
			event = (HolidayCalculatorEvent) ((ObjectMessage) aMessage).getObject();
		} catch (JMSException e) {
			throw new IllegalStateException("Ошибка получения содержимого сообщения", e);
		}
		_model.fireEvent(event);
	}

}
