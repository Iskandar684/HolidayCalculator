/**
 *
 */
package ru.iskandar.holiday.calculator.service.model;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Windows 7 x64
 *
 */
public class HolidayCalculatorEventListener implements MessageListener {

	@Override
	public void onMessage(Message aMessage) {
		// TODO Auto-generated method stub
		System.out.println("onEvent in model " + aMessage);
	}

}
