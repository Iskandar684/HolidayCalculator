package ru.iskandar.holiday.calculator.service.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Пользователь
 */
public class User implements Serializable {

	/**
	 * Идентфикатор для сериализации
	 */
	private static final long serialVersionUID = 7332495096773696543L;

	/** Имя */
	private final String _firstName;
	/** Фамилия */
	private final String _lastName;
	/** Отчество */
	private final String _patronymic;

	/** Идентификатор */
	private final UUID _uuid;

	/** Дата приема на работу */
	private final Date _employmentDate;

	/** Логин */
	private final String _login;

	/**
	 * Конструктор
	 */
	protected User(UUID aUUID, String aLastName, String aFirstName, String aPatronymic, Date aEmploymentDate,
			String aLogin) {
		Objects.requireNonNull(aUUID);
		Objects.requireNonNull(aLastName);
		Objects.requireNonNull(aFirstName);
		Objects.requireNonNull(aPatronymic);
		Objects.requireNonNull(aEmploymentDate);
		Objects.requireNonNull(aLogin);
		_uuid = aUUID;
		_firstName = aFirstName;
		_lastName = aLastName;
		_patronymic = aPatronymic;
		_employmentDate = aEmploymentDate;
		_login = aLogin;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return _lastName;
	}

	/**
	 * @return the patronymic
	 */
	public String getPatronymic() {
		return _patronymic;
	}

	/**
	 * @return the employmentDate
	 */
	public Date getEmploymentDate() {
		return _employmentDate;
	}

	/**
	 * @return the uuid
	 */
	public UserId getId() {
		return UserId.from(_uuid);
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return _login;
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
	public boolean equals(Object aObj) {
		if (this == aObj)
			return true;
		if (aObj == null)
			return false;
		if (getClass() != aObj.getClass())
			return false;
		User other = (User) aObj;
		if (_uuid == null) {
			if (other._uuid != null)
				return false;
		} else if (!_uuid.equals(other._uuid))
			return false;
		return true;
	}

}
