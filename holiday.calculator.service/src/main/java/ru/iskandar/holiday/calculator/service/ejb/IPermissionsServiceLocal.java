package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Локальный сервис проверки полномочий у пользователя
 */
public interface IPermissionsServiceLocal {

	/** JNDI имя */
	public static String JNDI_NAME = "holiday.calculator.service/PermissionsServiceBean!ru.iskandar.holiday.calculator.service.ejb.IPermissionsServiceLocal";

	/**
	 * Возвращает наличие указанных полномочий у указанного пользователя
	 *
	 * @param aPermissionId
	 *            идентификатор полномочия
	 * @param aUserId
	 *            идентификатор пользователя
	 * @return {@code true}, если пользователь имеет указанное полномочие;
	 *         {@code false}, если иначе
	 */
	public boolean hasPermission(PermissionId aPermissionId, UserId aUserId);

	/**
	 * Возвращает наличие указанных полномочий у текущего пользователя
	 *
	 * @param aPermissionId
	 *            идентификатор полномочия
	 * @return {@code true}, если пользователь имеет указанное полномочие;
	 *         {@code false}, если иначе
	 */
	public boolean hasPermission(PermissionId aPermissionId);

}
