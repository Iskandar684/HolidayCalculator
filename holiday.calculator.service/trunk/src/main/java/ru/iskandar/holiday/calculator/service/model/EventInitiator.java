package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Инициатор события
 */
public class EventInitiator {

	private final ClientId _clientId;

	private final User _user;

	/**
	 * Конструктор
	 */
	private EventInitiator(ClientId aClientId, User aUser) {
		Objects.requireNonNull(aClientId, "Не указан идентфикатор клиента");
		Objects.requireNonNull(aUser, "Не указан пользователь");
		_clientId = aClientId;
		_user = aUser;
	}

	public static EventInitiator create(ClientId aClientId, User aUser) {
		return new EventInitiator(aClientId, aUser);
	}

	/**
	 * @return the clientId
	 */
	public ClientId getClientId() {
		return _clientId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((_clientId == null) ? 0 : _clientId.hashCode());
		result = (prime * result) + ((_user == null) ? 0 : _user.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventInitiator other = (EventInitiator) obj;
		if (_clientId == null) {
			if (other._clientId != null)
				return false;
		} else if (!_clientId.equals(other._clientId))
			return false;
		if (_user == null) {
			if (other._user != null)
				return false;
		} else if (!_user.equals(other._user))
			return false;
		return true;
	}

}
