package ru.iskandar.holiday.calculator.service.model.user;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Идентификатор пользователя
 */
public class UserId implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -1773334763165903581L;

	/** Идентификатор пользователя */
	private final UUID _userUUID;

	/**
	 * Конструктор
	 *
	 * @param aUserUUID
	 *            идентификатор пользователя
	 */
	private UserId(UUID aUserUUID) {
		_userUUID = aUserUUID;
	}

	/**
	 * Создает идентификатор пользователя по UUID
	 *
	 * @param aUserUUID
	 *            UUID пользователя
	 * @return идентификатор пользователя
	 * @throws NullPointerException
	 *             если {@code aUserUUID} {@code null}
	 */
	public static UserId from(UUID aUserUUID) {
		Objects.requireNonNull(aUserUUID, "Не указан UUID пользователя");
		return new UserId(aUserUUID);
	}

	/**
	 * @return the userUUID
	 */
	public UUID getUUID() {
		return _userUUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((_userUUID == null) ? 0 : _userUUID.hashCode());
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
		UserId other = (UserId) obj;
		if (_userUUID == null) {
			if (other._userUUID != null) {
				return false;
			}
		} else if (!_userUUID.equals(other._userUUID)) {
			return false;
		}
		return true;
	}

}
