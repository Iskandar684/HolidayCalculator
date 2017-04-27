package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;

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

}
