package ru.iskandar.holiday.calculator.service.model;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Подписчик на серверные события
 */
public class ServerEventsSubscriber {

	/**
	 * Подписывает на серверные события
	 *
	 * @param aContext
	 *            контекст
	 * @param aMessageListener
	 *            слушатель серверных события
	 * @throws NamingException
	 * @throws JMSException
	 */
	public void subscribe(InitialContext aContext, MessageListener aMessageListener)
			throws NamingException, JMSException {

		String login = (String) aContext.getEnvironment().get(Context.SECURITY_PRINCIPAL);
		String password = (String) aContext.getEnvironment().get(Context.SECURITY_CREDENTIALS);

		ConnectionFactory connectionFactory = (ConnectionFactory) aContext.lookup("jms/RemoteConnectionFactory");

		try (Connection connection = connectionFactory.createConnection(login, password)) {
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic queue = (Topic) aContext.lookup("jms/topic/test");
			connection.start();
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(aMessageListener);
			while (!Thread.interrupted()) {
				Thread.yield();
			}
		}
	}

}
