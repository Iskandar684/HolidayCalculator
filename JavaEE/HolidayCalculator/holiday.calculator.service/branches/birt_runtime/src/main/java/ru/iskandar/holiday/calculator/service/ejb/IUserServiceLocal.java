package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;

import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;

/**
 * Сервис работы с пользователями
 */
public interface IUserServiceLocal {

	/**
	 * Возвращает текущего пользователя
	 *
	 * @return текущий пользователь
	 * @throws UserByLoginNotFoundException
	 *             если для логина вызывающего описание пользователя не найдено
	 */
	public User getCurrentUser();

	/**
	 * Возвращает всех пользователей
	 *
	 * @return все пользователи
	 */
	public Collection<User> getAllUsers();

	/**
	 * Создает нового пользователя
	 *
	 * @param aNewUserEntry
	 *            описание создаваемого пользователя
	 * @return созданный пользователь
	 */
	public User createUser(NewUserEntry aNewUserEntry);

	/**
	 * Ищет пользователя с указанным логином
	 *
	 * @param aLogin
	 *            логин
	 * @return найденный пользователь или {@code null}, если пользователь с
	 *         указанным логином не существует
	 * @throws NullPointerException
	 *             если aLogin {@code null}
	 */
	public User findUserByLogin(String aLogin);

}