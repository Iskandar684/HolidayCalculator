package ru.iskandar.holiday.calculator.service.entities;

import java.io.Serializable;
import java.util.Date;
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
public class UserEntity implements Serializable {

	/**
	 * Идентфикатор для сериализации
	 */
	private static final long serialVersionUID = -7202845772535656629L;

	/** Глобальный идентификатор */
	private UUID _uuid;

	/** Имя */
	private String _firstName;

	/** Фамилия */
	private String _lastName;

	/** Отчество */
	private String _patronymic;

	/** Логин пользователя */
	private String _login;

	/** Дата приема на работу */
	private Date _employmentDate;

	/**
	 * @return the uuid
	 */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
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
	@Column(name = "firstname")
	@NotNull(message = "Не указано имя")
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
	@Column(name = "lastname")
	@NotNull(message = "Не указана фамилия")
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
	@Column(name = "patronymic")
	@NotNull(message = "Не указано отчество")
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
	@Column(name = "login", unique = true)
	@NotNull(message = "Не указан логин")
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

	/**
	 * @return the employmentDate
	 */
	@Column(name = "employmentdate")
	@NotNull(message = "Не указан логин")
	public Date getEmploymentDate() {
		return _employmentDate;
	}

	/**
	 * @param aEmploymentDate
	 *            the employmentDate to set
	 */
	public void setEmploymentDate(Date aEmploymentDate) {
		_employmentDate = aEmploymentDate;
	}

}
