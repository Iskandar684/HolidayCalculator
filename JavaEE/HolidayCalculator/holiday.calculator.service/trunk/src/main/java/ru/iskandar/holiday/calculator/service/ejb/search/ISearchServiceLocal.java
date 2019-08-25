package ru.iskandar.holiday.calculator.service.ejb.search;

import ru.iskandar.holiday.calculator.service.model.search.ISearchResult;
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
	void addOrUpdate(User aUser) throws SearchServiceException;

	/**
	 * Выполняет поиск.
	 *
	 * @param aSearchText
	 *            строка поиска
	 * @return результат поиска
	 * @throws SearchServiceException
	 *             в случае ошибки поиска
	 */
	ISearchResult search(String aSearchText) throws SearchServiceException;

}
