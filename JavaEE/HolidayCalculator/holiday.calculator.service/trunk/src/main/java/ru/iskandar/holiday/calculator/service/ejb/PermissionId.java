package ru.iskandar.holiday.calculator.service.ejb;

import java.io.Serializable;
import java.util.Objects;

/**
 * Идентификатор полномочия
 */
public class PermissionId implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 4936714256790646532L;

	/** Идентификатор полномочия */
	private final String _id;

	/**
	 * Конструктор
	 *
	 * @param aUserUUID
	 *            идентификатор полномочия
	 */
	private PermissionId(String aUserUUID) {
		_id = aUserUUID;
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
	public static PermissionId from(String aUserUUID) {
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
		result = (prime * result) + ((_id == null) ? 0 : _id.hashCode());
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
		if (_id == null) {
			if (other._id != null) {
				return false;
			}
		} else if (!_id.equals(other._id)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return _id;
	}

}
