package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.model.User;
import ru.iskandar.holiday.calculator.service.model.UserFactory;

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
		// TODO временно
		String login1 = "user1";

		UserFactory factory1 = new UserFactory() {

			@Override
			protected UUID getUUID() {
				return UUID.fromString("ae155d60-916b-4d35-93a2-04b7ce46b6ba");
			}

			@Override
			protected String getFirstName() {

				return "Олег";
			}

			@Override
			protected String getLastName() {

				return "Анисимов";
			}

			@Override
			protected String getPatronymic() {

				return "Артёмович";
			}

		};
		User user1 = factory1.create();
		_loginToUserMap.put(login1, user1);

		String login2 = "user2";

		UserFactory factory2 = new UserFactory() {

			@Override
			protected UUID getUUID() {
				return UUID.fromString("13dd034b-94a9-491d-bdf4-d36cbace7140");
			}

			@Override
			protected String getFirstName() {

				return "Федор";
			}

			@Override
			protected String getLastName() {

				return "Григорьев";
			}

			@Override
			protected String getPatronymic() {

				return "Михалович";
			}

		};
		User user2 = factory2.create();

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

		User user = _loginToUserMap.get(login);
		if (user == null) {
			throw new IllegalStateException(String.format("Пользователь с логином %s не найден", login));
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<User> getAllUsers() {
		return Collections.unmodifiableCollection(_loginToUserMap.values());
	}

}
