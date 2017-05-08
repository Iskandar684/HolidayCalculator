package ru.iskandar.holiday.calculator.service.ejb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Тест {@link UserServiceBean}
 */
public class UserServiceBeanTest extends JPAHibernateTest {

	private UserServiceBean _service;

	@Before
	public void setUp() {
		EntityManager em = getEntityManager();
		_service = new UserServiceBean(em);
	}

	/**
	 *
	 */
	@Test
	public void testGetAllUsersForEmpty() {
		Collection<User> users = _service.getAllUsers();
		assertNotNull(users);
		assertTrue(users.isEmpty());
	}

}
