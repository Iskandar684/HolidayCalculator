package ru.iskandar.holiday.calculator.service.entities;

import java.util.Date;
import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserId;

/**
 *
 */
public class DOBasedUserEntityFactory extends UserEntityFactory {

	private final User _user;

	/**
	 * Конструктор
	 */
	public DOBasedUserEntityFactory(User aUser) {
		Objects.requireNonNull(aUser);
		_user = aUser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UserId getId() {
		return _user.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getFirstName() {
		return _user.getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getLastName() {
		return _user.getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPatronymic() {
		return _user.getPatronymic();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getLogin() {
		return _user.getLogin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getEmploymentDate() {
		return _user.getEmploymentDate();
	}

}
