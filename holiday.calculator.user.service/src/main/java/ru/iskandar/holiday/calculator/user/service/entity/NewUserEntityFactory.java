package ru.iskandar.holiday.calculator.user.service.entity;

import java.util.Date;
import java.util.Objects;

import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

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

	@Override
	protected String getNote() {
		return _user.getNote();
	}

}
