package ru.iskandar.holiday.calculator.service.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Описание для создания нового пользователя
 */
public class NewUserEntry implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 7550226147547113824L;

	/** Имя */
	private String _firstName;
	/** Фамилия */
	private String _lastName;
	/** Отчество */
	private String _patronymic;

	/** Дата приема на работу */
	private Date _employmentDate = new Date();

	/** Логин */
	private String _login;

	/** Пароль */
	private String _password;

	/**
	 * Конструктор
	 */
	public NewUserEntry(String aLastName, String aFirstName, String aPatronymic, Date aEmploymentDate, String aLogin) {
		Objects.requireNonNull(aLastName);
		Objects.requireNonNull(aFirstName);
		Objects.requireNonNull(aPatronymic);
		Objects.requireNonNull(aEmploymentDate);
		Objects.requireNonNull(aLogin);
		_firstName = aFirstName;
		_lastName = aLastName;
		_patronymic = aPatronymic;
		_employmentDate = aEmploymentDate;
		_login = aLogin;
	}

	/**
	 * Конструктор
	 */
	public NewUserEntry() {
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
	 * @return the login
	 */
	public String getLogin() {
		return _login;
	}

	/**
	 * @param aFirstName
	 *            the firstName to set
	 */
	public void setFirstName(String aFirstName) {
		_firstName = aFirstName;
	}

	/**
	 * @param aLastName
	 *            the lastName to set
	 */
	public void setLastName(String aLastName) {
		_lastName = aLastName;
	}

	/**
	 * @param aPatronymic
	 *            the patronymic to set
	 */
	public void setPatronymic(String aPatronymic) {
		_patronymic = aPatronymic;
	}

	/**
	 * @param aEmploymentDate
	 *            the employmentDate to set
	 */
	public void setEmploymentDate(Date aEmploymentDate) {
		_employmentDate = aEmploymentDate;
	}

	/**
	 * @param aLogin
	 *            the login to set
	 */
	public void setLogin(String aLogin) {
		_login = aLogin;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return _password;
	}

	/**
	 * @param aPassword
	 *            the password to set
	 */
	public void setPassword(String aPassword) {
		_password = aPassword;
	}

	public boolean isEmpty() {
		if ((getLogin() == null) || getLogin().isEmpty()) {
			return true;
		}
		if ((getFirstName() == null) || getFirstName().isEmpty()) {
			return true;
		}
		if ((getLastName() == null) || getLastName().isEmpty()) {
			return true;
		}
		if ((getPatronymic() == null) || getPatronymic().isEmpty()) {
			return true;
		}
		if (getEmploymentDate() == null) {
			return true;
		}

		if ((getPassword() == null) || getPassword().isEmpty()) {
			return true;
		}
		return false;
	}

}
