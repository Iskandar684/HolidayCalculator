package ru.iskandar.holiday.calculator.service.model.user;

import java.util.Date;
import java.util.Objects;

/**
 *
 */
public abstract class UserEntityFactory {

	public UserEntity create() {

		UserId id = getId();

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
		entity.setEmploymentDate(employmentDate);
		entity.setFirstName(firstName);
		entity.setLastName(lastName);
		entity.setLogin(login);
		entity.setPatronymic(patronymic);
		// У создаваемого адреса id = null
		entity.setUuid(id != null ? id.getUUID() : null);
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
