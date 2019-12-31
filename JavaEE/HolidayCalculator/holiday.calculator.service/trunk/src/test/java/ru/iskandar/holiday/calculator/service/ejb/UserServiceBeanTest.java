package ru.iskandar.holiday.calculator.service.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.iskandar.holiday.calculator.service.ejb.search.ISearchServiceLocal;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Тест {@link UserServiceBean}
 */
public class UserServiceBeanTest extends JPAHibernateTest {

	private UserServiceBean _service;

	/** Сервис поиска */
	private ISearchServiceLocal _searchServiceLocal;

	/** Менеджер управления полномочиями пользователей */
	private IUserPermissionsManager _userPermissionsManager;

	@Before
	public void setUp() {
		_searchServiceLocal = mock(ISearchServiceLocal.class);
		_userPermissionsManager = mock(IUserPermissionsManager.class);
		EntityManager em = getEntityManager();
		_service = new UserServiceBean(em, _searchServiceLocal, _userPermissionsManager);
		em.getTransaction().begin();
	}

	@After
	public void after() {
		EntityManager em = getEntityManager();
		em.getTransaction().commit();
	}

	/**
	 *
	 */
	@Test
	public void testCreateUser() {
		String firstName = "Андрей";
		String lastName = "Большаков";
		String patronymic = "Юлианович";
		String login = "user1";
		NewUserEntry entry = new NewUserEntry(lastName, firstName, patronymic, new Date(), login);
		User user = _service.createUser(entry, Collections.emptySet());
		assertNotNull(user);
		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(patronymic, user.getPatronymic());
		assertEquals(login, user.getLogin());
		assertNotNull(user.getId());
	}

	/**
	 *
	 */
	@Test
	public void testFindUserByLogin() {
		String firstName = "Андрей";
		String lastName = "Большаков";
		String patronymic = "Юлианович";
		String login = "user3";
		NewUserEntry entry = new NewUserEntry(lastName, firstName, patronymic, new Date(), login);
		User userByLogin = _service.createUser(entry, Collections.emptySet());
		User findedUser = _service.findUserByLogin(login);
		assertEquals(userByLogin, findedUser);
	}

	/**
	 *
	 */
	@Test
	public void testGetAllUsers() {
		String firstName = "Андрей";
		String lastName = "Большаков";
		String patronymic = "Юлианович";
		String login = "user2";
		NewUserEntry entry = new NewUserEntry(lastName, firstName, patronymic, new Date(), login);
		User user = _service.createUser(entry, Collections.emptySet());

		Collection<User> users = _service.getAllUsers();
		assertNotNull(users);
		assertTrue(users.contains(user));
	}

}
