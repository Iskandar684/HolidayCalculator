package ru.iskandar.holiday.calculator.service.entities;

import java.util.Date;
import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.user.UserId;

/**
 *
 */
public abstract class UserEntityFactory {

	public UserEntity create() {

		UserId id = getId();
		Objects.requireNonNull(id);
		String firstName = getFirstName();
		Objects.requireNonNull(firstName);
		String lastName = getLastName();
		Objects.requireNonNull(lastName);

		String patronymic = getPatronymic();
		Objects.requireNonNull(patronymic);

		String login = getLogin();
		Objects.requireNonNull(login);

		Date employmentDate = getEmploymentDate();
		Objects.requireNonNull(employmentDate);

		UserEntity entity = new UserEntity();
		return entity;
	}

	/**
	 * @return the uuid
	 */
	protected abstract UserId getId();

	/**
	 * @return the firstName
	 */
	protected abstract String getFirstName();

	/**
	 * @return the lastName
	 */
	protected abstract String getLastName();

	/**
	 * @return the patronymic
	 */
	protected abstract String getPatronymic();

	/**
	 * @return the login
	 */
	protected abstract String getLogin();

	/**
	 * @return the employmentDate
	 */
	protected abstract Date getEmploymentDate();

}
