package ru.iskandar.holiday.calculator.service.ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сервис работы с полномочиями
 */
@Stateless
@Local(IPermissionsServiceLocal.class)
public class PermissionsServiceBean implements IPermissionsServiceLocal {

	/** Контекст сессии */
	@Resource
	private SessionContext _context;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(PermissionId aPermissionId, UserId aUserId) {
		throw new RuntimeException("Метод не реализован");
	}

	/**
	 * {@inheritDoc}
	 */
	@PermitAll
	@Override
	public boolean hasPermission(PermissionId aPermissionId) {
		boolean inRole = _context.isCallerInRole(aPermissionId.getId());
		return inRole;
	}

}
