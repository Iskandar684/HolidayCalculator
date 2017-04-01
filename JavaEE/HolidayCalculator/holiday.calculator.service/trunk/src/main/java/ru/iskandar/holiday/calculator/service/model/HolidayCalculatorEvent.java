package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;

/**
 * Событие сервиса учета отгулов
 */
public class HolidayCalculatorEvent implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 7263240058277798176L;

	/**
	 * Идентификатор клиента инициатора события. {@code null}, если инициатором
	 * события является не пользователь
	 */
	private ClientId _initiator;

	/**
	 * @return the initiator
	 */
	public ClientId getInitiator() {
		return _initiator;
	}

	/**
	 * @param aInitiator
	 *            the initiator to set
	 */
	public void setInitiator(ClientId aInitiator) {
		_initiator = aInitiator;
	}

}
