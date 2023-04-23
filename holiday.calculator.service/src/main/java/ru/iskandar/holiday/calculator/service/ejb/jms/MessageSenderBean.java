package ru.iskandar.holiday.calculator.service.ejb.jms;

import static ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorJMSConstants.DESTINATION_ID;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.ws.rs.Path;

import lombok.extern.jbosslog.JBossLog;
import ru.iskandar.holiday.calculator.service.event.EventSendersProvider;
import ru.iskandar.holiday.calculator.service.event.IHolidayCalculatorEventSender;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;

/**
 * Сервис отправки сообщений
 */
@Stateless
@Path("/")
@JBossLog
public class MessageSenderBean {

	/** Контекст JMS */
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext _context;

	/** Очередь */
	@Resource(name = DESTINATION_ID)
	private Destination _destination;

	@EJB
	private EventSendersProvider _eventSendersProvider;

	/**
	 * Отправляет сообщение
	 *
	 * @param aEvent сообщение
	 * @throws JMSException ошибка отправки сообщения
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

		List<IHolidayCalculatorEventSender> senders = _eventSendersProvider.getSenders();
		for (IHolidayCalculatorEventSender sender : senders) {
			try {
				sender.send(aEvent);
			} catch (Exception e) {
				log.errorf(e, "Ошибка оповещения обработчиком %s: %s.", sender.getClass(), e.getMessage());
			}
		}
	}

}
