package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.User;

/**
 * Сервис работы с пользователями
 */
public interface IUserServiceLocal {

	/**
	 * Возвращает текущего пользователя
	 *
	 * @return текущий пользователь
	 */
	public User getCurrentUser();

}
