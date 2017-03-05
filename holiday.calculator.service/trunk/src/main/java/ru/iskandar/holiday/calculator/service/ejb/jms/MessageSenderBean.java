/**
 *
 */
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

/**
 *
 */
@Stateless
public class MessageSenderBean {

	@Inject
	// @JMSConnectionFactory("jms/QueueConnectionFactory")
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext _context;

	// @Resource(name = "jms/ShippingRequestQueue")
	@Resource(name = "java:jboss/exported/jms/topic/test")
	// @Resource(name = "queue/ShippingRequestQueue")
	private Destination _destination;

	public void send() throws JMSException {
		System.out.println("send");
		ShippingRequest shippingRequest = new ShippingRequest();

		ObjectMessage om = _context.createObjectMessage();
		om.setObject(shippingRequest);

		JMSProducer producer = _context.createProducer();
		Objects.requireNonNull(_destination, "Не указан _destination");
		producer.send(_destination, om);
	}

}
