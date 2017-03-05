package ru.iskandar.holiday.calculator.service.ejb.jms;

import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;

/**
 * Сервис отправки сообщений
 */
@Stateless
public class MessageSenderBean {

	/** JNDI имя очереди */
	private static final String DESTINATION_ID = "java:jboss/exported/jms/topic/test";

	/** Контекст JMS */
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext _context;

	/** Очередь */
	@Resource(name = DESTINATION_ID)
	private Destination _destination;

	/**
	 * Отправляет сообщение
	 *
	 * @param aEvent
	 *            сообщение
	 * @throws JMSException
	 *             ошибка отправки сообщения
	 */
	public void send(HolidayCalculatorEvent aEvent) throws JMSException {
		Objects.requireNonNull(aEvent, "Не указано сообщение");
		if (_destination == null) {
			throw new IllegalStateException(String.format("Отсутствует очередь; destinationId='%s'", DESTINATION_ID));
		}

		ObjectMessage om = _context.createObjectMessage();
		om.setObject(aEvent);

		JMSProducer producer = _context.createProducer();

		producer.send(_destination, om);
	}

}
