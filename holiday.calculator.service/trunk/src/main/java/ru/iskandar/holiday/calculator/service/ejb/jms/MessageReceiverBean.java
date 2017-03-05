package ru.iskandar.holiday.calculator.service.ejb.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topic/test") })
public class MessageReceiverBean implements MessageListener {

	@Override
	public void onMessage(Message aMessage) {
		System.out.println("onMessage " + aMessage);
	}

}
