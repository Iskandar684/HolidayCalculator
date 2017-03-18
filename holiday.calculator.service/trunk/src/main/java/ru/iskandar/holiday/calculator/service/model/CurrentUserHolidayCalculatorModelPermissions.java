/**
 *
 */
package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.ejb.IPermissionsService;
import ru.iskandar.holiday.calculator.service.ejb.PermissionId;

/**
 * @author Искандар
 *
 */
public class CurrentUserHolidayCalculatorModelPermissions implements IHolidayCalculatorModelPermissions {

	private final IPermissionsService _permissionsService;

	/**
	 *
	 */
	public CurrentUserHolidayCalculatorModelPermissions(IPermissionsService aPermissionsService) {
		Objects.requireNonNull(aPermissionsService);
		_permissionsService = aPermissionsService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConsider() {
		boolean canConsider = _permissionsService.hasPermission(PermissionId.from(Permissions.CONSIDER.getId()));
		return canConsider;
	}

}
