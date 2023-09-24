package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.UserByIdNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;
import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сервис работы с пользователями
 */
public interface IUserService {
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
	 * @param aPermissions
	 *            полномочия
	 * @return созданный пользователь
	 */
	public User createUser(NewUserEntry aNewUserEntry, Set<PermissionId> aPermissions);

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

	/**
	 * Меняет примечание пользователя.
	 *
	 * @param aUserId
	 *            идентификатор пользователя
	 * @param aNewNote
	 *            новое примечание
	 * @throws UserByIdNotFoundException
	 *             если пользователь по указанному идентификатору не найден
	 */
	void changeNote(UserId aUserId, String aNewNote);

        Map<UserId, User> getUsersById(Collection<UserId> aIds);

}
