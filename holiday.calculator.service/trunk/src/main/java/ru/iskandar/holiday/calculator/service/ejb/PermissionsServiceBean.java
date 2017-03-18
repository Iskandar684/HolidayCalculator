package ru.iskandar.holiday.calculator.service.ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.model.UserId;

/**
 * Сервис работы с полномочиями
 */
@Stateless
@Remote(IPermissionsService.class)
public class PermissionsServiceBean implements IPermissionsService {

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
