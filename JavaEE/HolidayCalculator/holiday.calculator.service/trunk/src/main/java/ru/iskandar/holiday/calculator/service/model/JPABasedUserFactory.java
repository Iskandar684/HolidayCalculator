package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;
import java.util.UUID;

import ru.iskandar.holiday.calculator.service.entities.UserJPA;

/**
 * Фабрика создания описания пользователя на основе сущности пользователя
 */
public class JPABasedUserFactory extends UserFactory {

	/** Сущность пользователя */
	private final UserJPA _userEntity;

	/**
	 * Конструктор
	 *
	 * @param aUserJPA
	 *            сущность пользователя
	 */
	public JPABasedUserFactory(UserJPA aUserJPA) {
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

}
