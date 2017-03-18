package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.model.User;

/**
 * Сервис работы с пользователями
 *
 */
@Stateless
@Local(IUserServiceLocal.class)
public class UserServiceBean implements IUserServiceLocal {

	/** Контекст сессии */
	@Resource
	private SessionContext _context;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PermitAll
	public User getCurrentUser() {
		// TODO реализовать
		Principal principal = _context.getCallerPrincipal();
		String login = principal.getName();
		System.out.println("currentUserLogin " + login);
		return new User("Анисимов", "Олег", "Артёмович", login);
	}

}
