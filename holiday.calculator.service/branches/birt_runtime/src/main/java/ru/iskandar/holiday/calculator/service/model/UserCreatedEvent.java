package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Событие создания пользователя
 */
public class UserCreatedEvent extends HolidayCalculatorEvent {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 7395233610755325868L;

	/** Созданный пользователь */
	private final User _createdUser;

	/**
	 * Конструктор
	 */
	public UserCreatedEvent(User aCreatedUser) {
		Objects.requireNonNull(aCreatedUser);
		_createdUser = aCreatedUser;
	}

	/**
	 * @return the createdUser
	 */
	public User getCreatedUser() {
		return _createdUser;
	}

}
