package ru.iskandar.holiday.calculator.service.ejb;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Идентификатор полномочия
 */
public class PermissionId implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 4936714256790646532L;

	/** Идентификатор полномочия */
	private final UUID _uuid;

	/**
	 * Конструктор
	 *
	 * @param aUserUUID
	 *            идентификатор полномочия
	 */
	private PermissionId(UUID aUserUUID) {
		_uuid = aUserUUID;
	}

	/**
	 * Создает идентификатор полномочия по UUID
	 *
	 * @param aUserUUID
	 *            UUID полномочия
	 * @return идентификатор полномочия
	 * @throws NullPointerException
	 *             если {@code aUserUUID} {@code null}
	 */
	public static PermissionId from(UUID aUserUUID) {
		Objects.requireNonNull(aUserUUID, "Не указан UUID полномочия");
		return new PermissionId(aUserUUID);
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PermissionId other = (PermissionId) obj;
		if (_uuid == null) {
			if (other._uuid != null) {
				return false;
			}
		} else if (!_uuid.equals(other._uuid)) {
			return false;
		}
		return true;
	}

}
