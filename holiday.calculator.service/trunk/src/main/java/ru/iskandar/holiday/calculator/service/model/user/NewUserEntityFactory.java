package ru.iskandar.holiday.calculator.service.model.user;

import java.util.Date;
import java.util.Objects;

/**
 *
 */
public class NewUserEntityFactory extends UserEntityFactory {

	private final NewUserEntry _user;

	/**
	 * Конструктор
	 */
	public NewUserEntityFactory(NewUserEntry aUser) {
		Objects.requireNonNull(aUser);
		_user = aUser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UserId getId() {
		return null;
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
