package ru.iskandar.holiday.calculator.service.model.user;

import java.util.Date;
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
		Date empDate = getEmploymentDate();
		String login = getLogin();
		Objects.requireNonNull(uuid, "Не указан UUID создаваемого пользователя");
		Objects.requireNonNull(firstName, "Не указано имя создаваемого пользователя");
		Objects.requireNonNull(lastName, "Не указана фамилия создаваемого пользователя");
		Objects.requireNonNull(patronymic, "Не указано отчество создаваемого пользователя");
		Objects.requireNonNull(empDate, "Не указана дата приема на работу создаваемого пользователя");
		Objects.requireNonNull(login, "Не указан логин создаваемого пользователя");
		User user = new User(uuid, lastName, firstName, patronymic, empDate, login);
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

	/**
	 * Возвращает дату приема на работу
	 *
	 * @return дата приема на работу; не может быть {@code null}
	 */
	protected abstract Date getEmploymentDate();

	/**
	 * Возвращает логин пользователя
	 *
	 * @return логин; не может быть {@code null}
	 */
	protected abstract String getLogin();

}
