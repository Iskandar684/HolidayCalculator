package ru.iskandar.holiday.calculator.service.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

import org.jboss.logging.Logger;

/**
 * Менеджер управления полномочиями пользователей.
 */
public class UserPermissionsManager implements IUserPermissionsManager {

	/** Логгер */
	private static final Logger LOG = Logger.getLogger(UserPermissionsManager.class.getName());

	@Override
	public void addOrChangePermissions(String aUser, String aPassword, Set<PermissionId> aPermissions)
			throws HolidayCalculatorException {
		LOG.info("Назначение полномочий " + aPermissions + " пользователю " + aUser);
		String jbossHome = System.getProperty("jboss.home.dir");
		StringBuilder builder = new StringBuilder();
		builder.append(jbossHome);
		// FIXME linux only
		builder.append("/bin/add-user.sh -a -u ");
		builder.append(aUser);
		builder.append(" -p ");
		builder.append(aPassword);
		builder.append(" -g ");
		builder.append("guest");
		Iterator<PermissionId> it = aPermissions.iterator();
		while (it.hasNext()) {
			builder.append(",");
			builder.append(it.next().getId());
		}
		try {
			Process process = Runtime.getRuntime().exec(builder.toString());
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					LOG.info(line);
				}
			}
		} catch (IOException e) {
			throw new HolidayCalculatorException(
					String.format("Ошибка назначения полномочий %s пользователю %s.", aPermissions, aUser), e);
		}
	}

}
