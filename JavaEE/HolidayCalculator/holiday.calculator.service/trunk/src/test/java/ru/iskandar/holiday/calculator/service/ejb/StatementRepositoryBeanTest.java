package ru.iskandar.holiday.calculator.service.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.iskandar.holiday.calculator.service.ejb.search.ISearchServiceLocal;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public class StatementRepositoryBeanTest extends JPAHibernateTest {

	private final StatementRepositoryBean _service = new StatementRepositoryBean();

	private UserServiceBean _userService;

	/** Сервис поиска */
	private ISearchServiceLocal _searchServiceLocal;

	private User user;

	@Before
	public void setUp() {
		_searchServiceLocal = mock(ISearchServiceLocal.class);
		EntityManager em = getEntityManager();
		_userService = new UserServiceBean(em, _searchServiceLocal);
		EntityManagerInjector.inject(_service, em);
		EntityManagerInjector.inject(_userService, em);
		em.getTransaction().begin();
		String firstName = "Смирнов";
		String lastName = "Павел";
		String patronymic = "Владимирович";
		String login = "user" + UUID.randomUUID().toString();
		NewUserEntry entry = new NewUserEntry(lastName, firstName, patronymic, new Date(), login);
		user = _userService.createUser(entry, Collections.emptySet());
	}

	/**
	 *
	 */
	@Test
	public void testCreateHolidayStatement() {
		Date date = new Date();

		HolidayStatementEntry entry = new HolidayStatementEntry(Collections.singleton(date), user);
		HolidayStatement statement = _service.createHolidayStatement(entry);

		assertNotNull(statement);
		assertEquals(entry.getConsider(), statement.getConsider());
		assertEquals(entry.getConsiderDate(), statement.getConsiderDate());
		assertEquals(entry.getCreateDate(), statement.getCreateDate());
		assertEquals(entry.getAuthor(), statement.getAuthor());
		Set<Date> days = statement.getDays();
		assertNotNull(days);
		assertEquals(1, days.size());
		assertEquals(date, days.iterator().next());
	}

	@After
	public void after() {
		EntityManager em = getEntityManager();
		em.getTransaction().commit();
	}

}
