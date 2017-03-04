package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.service.model.UserId;

/**
 * Сервис проверки полномочий у пользователя
 */
public interface IPermissionsService {

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

}
