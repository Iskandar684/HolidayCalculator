/**
 *
 */
package ru.iskandar.holiday.calculator.service.model;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Windows 7 x64
 *
 */
public class ServerEventsSubscriber {

	public void subscribe(InitialContext context, MessageListener aMessageListener)
			throws NamingException, JMSException {
		// Connection connection = null;
		// try {
		System.out.println("subscribe in model");

		System.out.println("Get connection facory");
		// ConnectionFactory connectionFactory = (ConnectionFactory) context
		// .lookup("ConnectionFactory");

		// ConnectionFactory connectionFactory = (ConnectionFactory) context
		// .lookup("java:jboss/exported/jms/RemoteConnectionFactory");
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");

		System.out.println("Create connection");
		// TODO получить логин и пароль из initinalContext
		try (Connection connection = connectionFactory.createConnection("testuser2", "testpassword2")) {
			System.out.println("ConnectionFactory " + connectionFactory);
			System.out.println("Create session");
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("Lookup queue");
			// Queue queue = (Queue) context.lookup("/queue/HelloWorldQueue");
			// Queue queue = (Queue)
			// context.lookup("java:/jms/queue/ExpiryQueue");

			// Queue queue = session.createQueue("java:jms/queue/ExpiryQueue");
			// Queue queue = (Queue) context.lookup("jms/queue/test");
			Topic queue = (Topic) context.lookup("jms/topic/test");
			System.out.println("queue=" + queue);

			System.out.println("Start connection");
			connection.start();
			System.out.println("Create consumer");
			MessageConsumer consumer = session.createConsumer(queue);
			System.out.println("set message listener");
			consumer.setMessageListener(aMessageListener);
			while (!Thread.interrupted()) {
				Thread.yield();
			}
		}
	}

}
