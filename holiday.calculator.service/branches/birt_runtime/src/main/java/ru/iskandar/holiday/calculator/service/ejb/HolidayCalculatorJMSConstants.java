package ru.iskandar.holiday.calculator.service.ejb;

/**
 * Константы JMS сообщения сервис учета отгулов 
 */
public final class HolidayCalculatorJMSConstants {
	
	/** JNDI имя очереди */
	public static final String DESTINATION_ID = "java:jboss/exported/jms/topic/holidaycalculator";
	
	
	/** JNDI имя очереди для удаленных вызовов */
	public static final String REMOTE_DESTINATION_ID = "jms/topic/holidaycalculator";
	
	
	/**
	 * Конструктор
	 */
	private HolidayCalculatorJMSConstants(){}

}
