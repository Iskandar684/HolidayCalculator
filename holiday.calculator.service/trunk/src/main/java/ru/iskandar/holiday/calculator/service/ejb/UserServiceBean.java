package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
	// TODO имитация базы
	private static final Map<String, User> _loginToUserMap = new HashMap<>();

	/**
	 *
	 */
	public UserServiceBean() {
		String login1 = "user1";
		User user1 = new User("Анисимов", "Олег", "Артёмович", login1);
		_loginToUserMap.put(login1, user1);

		String login2 = "user2";
		User user2 = new User("Григорьев", "Федор", "Михалович", login1);
		_loginToUserMap.put(login2, user2);
	}

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

		User user = _loginToUserMap.get(login);
		if (user == null) {
			throw new IllegalStateException(String.format("Пользователь с логином %s не найден", login));
		}
		return user;
	}

}
