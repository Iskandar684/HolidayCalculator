package ru.iskandar.holiday.calculator.service.ejb.search;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Служба поиска.
 */
public interface ISearchServiceLocal {

	/**
	 * Добавляет пользователя.
	 *
	 * @param aUser
	 *            пользователь
	 * @throws SearchServiceException
	 *             в случае ошибки добавления
	 */
	public void addOrUpdate(User aUser) throws SearchServiceException;

}
