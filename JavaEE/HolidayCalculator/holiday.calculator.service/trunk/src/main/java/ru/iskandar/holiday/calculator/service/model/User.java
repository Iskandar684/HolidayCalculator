package ru.iskandar.holiday.calculator.service.model;

/**
 * Пользователь
 *
 */
public class User {

	/** Имя */
	private final String _firstName;
	/** Фамилия */
	private final String _lastName;
	/** Отчество */
	private final String _patronymic;

	/**
	 * Конструктор
	 */
	protected User(String aLastName, String aFirstName, String aPatronymic) {
		_firstName = aFirstName;
		_lastName = aLastName;
		_patronymic = aPatronymic;
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

}
