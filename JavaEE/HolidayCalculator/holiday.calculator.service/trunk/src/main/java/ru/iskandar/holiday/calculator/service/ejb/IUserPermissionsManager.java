package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Set;

/**
 * Менеджер управления полномочиями пользователей.
 */
public interface IUserPermissionsManager {

	/**
	 * Назначает полномочия пользователю.
	 *
	 * @param aUser
	 *            логин пользователя
	 * @param aPassword
	 *            пароль пользователя
	 * @param aPermissions
	 *            полномочия
	 * @throws HolidayCalculatorException
	 *             в случае ошибки
	 */
	void addOrChangePermissions(String aUser, String aPassword, Set<PermissionId> aPermissions)
			throws HolidayCalculatorException;
}
