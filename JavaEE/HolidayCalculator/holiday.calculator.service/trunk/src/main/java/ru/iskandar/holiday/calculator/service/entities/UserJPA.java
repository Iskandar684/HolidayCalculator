package ru.iskandar.holiday.calculator.service.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_user")
public class UserJPA {

	/** Глобальный идентификатор */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
	private UUID _uuid;

	/** Имя */
	@Column(name = "firstname")
	@NotNull(message = "Не указано имя")
	private String _firstName;

	/** Фамилия */
	@Column(name = "lastname")
	@NotNull(message = "Не указана фамилия")
	private String _lastName;

	/** Отчество */
	@Column(name = "patronymic")
	@NotNull(message = "Не указано отчество")
	private String _patronymic;

	/** Логин пользователя */
	@Column(name = "login", unique = true)
	@NotNull(message = "Не указан логин")
	private String _login;

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return _uuid;
	}

	/**
	 * @param aUuid
	 *            the uuid to set
	 */
	public void setUuid(UUID aUuid) {
		_uuid = aUuid;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * @param aFirstName
	 *            the firstName to set
	 */
	public void setFirstName(String aFirstName) {
		_firstName = aFirstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return _lastName;
	}

	/**
	 * @param aLastName
	 *            the lastName to set
	 */
	public void setLastName(String aLastName) {
		_lastName = aLastName;
	}

	/**
	 * @return the patronymic
	 */
	public String getPatronymic() {
		return _patronymic;
	}

	/**
	 * @param aPatronymic
	 *            the patronymic to set
	 */
	public void setPatronymic(String aPatronymic) {
		_patronymic = aPatronymic;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return _login;
	}

	/**
	 * @param aLogin
	 *            the login to set
	 */
	public void setLogin(String aLogin) {
		_login = aLogin;
	}

}
