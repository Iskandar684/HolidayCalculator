package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Пользователь
 *
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

	/** Логин */
	private final String _login;

	private final UUID _uuid;

	/**
	 * Конструктор
	 */
	// TODO protected
	public User(UUID aUUID, String aLastName, String aFirstName, String aPatronymic, String aLogin) {
		_uuid = aUUID;
		_firstName = aFirstName;
		_lastName = aLastName;
		_patronymic = aPatronymic;
		_login = aLogin;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public String getPatronymic() {
		return _patronymic;
	}

	/**
	 * Возвращает количество не использованных дней отпуска в этом периоде
	 *
	 * @return количество дней
	 */
	public int getLeaveCount() {
		return 28;
	}

	/**
	 * Возвращает количество исходящих дней отпуска. Это количество дней, на
	 * которое уменьшется количество дней отпуска в этом периоде, после того как
	 * заявление на отпуск будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отпуска.
	 */
	public int getOutgoingLeaveCount() {
		return 14;
	}

	/**
	 * Возвращает дату начала следующего периода
	 *
	 * @return дата начала следующего периода
	 */
	public Date getNextLeaveStartDate() {
		return new Date();
	}

	/**
	 * Возвращает логин
	 *
	 * @return логин
	 */
	protected String getLogin() {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (_uuid == null) {
			if (other._uuid != null)
				return false;
		} else if (!_uuid.equals(other._uuid))
			return false;
		return true;
	}

}
