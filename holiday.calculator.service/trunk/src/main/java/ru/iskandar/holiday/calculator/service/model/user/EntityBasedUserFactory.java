package ru.iskandar.holiday.calculator.service.model.user;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Фабрика создания описания пользователя на основе сущности пользователя
 */
public class EntityBasedUserFactory extends UserFactory {

	/** Сущность пользователя */
	private final UserEntity _userEntity;

	/**
	 * Конструктор
	 *
	 * @param aUserJPA
	 *            сущность пользователя
	 */
	public EntityBasedUserFactory(UserEntity aUserJPA) {
		Objects.requireNonNull(aUserJPA);
		_userEntity = aUserJPA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UUID getUUID() {
		return _userEntity.getUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getFirstName() {
		return _userEntity.getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getLastName() {
		return _userEntity.getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPatronymic() {
		return _userEntity.getPatronymic();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getEmploymentDate() {
		return _userEntity.getEmploymentDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getLogin() {
		return _userEntity.getLogin();
	}

}
