package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Идентификатор клиента
 */
public final class ClientId {

	private final UUID _uuid;

	private ClientId(UUID aUUID) {
		_uuid = aUUID;
	}

	public static ClientId fromUUID(UUID aUUID) {
		Objects.requireNonNull(aUUID);
		return new ClientId(aUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((_uuid == null) ? 0 : _uuid.hashCode());
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
		ClientId other = (ClientId) obj;
		if (_uuid == null) {
			if (other._uuid != null)
				return false;
		} else if (!_uuid.equals(other._uuid))
			return false;
		return true;
	}

}
