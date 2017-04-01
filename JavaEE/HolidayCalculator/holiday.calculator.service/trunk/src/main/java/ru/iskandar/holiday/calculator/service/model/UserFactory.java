package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Фабрика создания описания пользователя
 */
public abstract class UserFactory {

	/**
	 * Создает пользователя
	 *
	 * @return пользователь
	 */
	public final User create() {
		String firstName = getFirstName();
		String lastName = getLastName();
		String patronymic = getPatronymic();
		UUID uuid = getUUID();
		Objects.requireNonNull(uuid, "Не указан UUID создаваемого пользователя");
		Objects.requireNonNull(firstName, "Не указано имя создаваемого пользователя");
		Objects.requireNonNull(lastName, "Не указана фамилия создаваемого пользователя");
		Objects.requireNonNull(patronymic, "Не указано отчество создаваемого пользователя");
		User user = new User(uuid, lastName, firstName, patronymic);
		return user;
	}

	/**
	 * Возвращает UUID пользователя
	 *
	 * @return UUID пользователя; не может быть {@code null}
	 */
	protected abstract UUID getUUID();

	/**
	 * Возвращает имя пользователя
	 *
	 * @return имя пользователя; не может быть {@code null}
	 */
	protected abstract String getFirstName();

	/**
	 * Возвращает фамилию пользователя
	 *
	 * @return фамилия пользователя; не может быть {@code null}
	 */
	protected abstract String getLastName();

	/**
	 * Возвращает отчество пользователя
	 *
	 * @return отчество пользователя; не может быть {@code null}
	 */
	protected abstract String getPatronymic();

}
