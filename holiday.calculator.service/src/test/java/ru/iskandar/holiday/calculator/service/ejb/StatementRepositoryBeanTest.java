package ru.iskandar.holiday.calculator.service.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StatementRepositoryBeanTest extends JPAHibernateTest {

    @Mock
    private IUserServiceLocal _userService;

    @InjectMocks
    private StatementRepositoryBean _service = new StatementRepositoryBean();

    private User user;

    @Before
    public void setUp() {
        EntityManager em = getEntityManager();
        EntityManagerInjector.inject(_service, em);
        em.getTransaction().begin();
        String firstName = "Смирнов";
        String lastName = "Павел";
        String patronymic = "Владимирович";
        String login = "user" + UUID.randomUUID().toString();
        user = User.builder().firstName(firstName).lastName(lastName).patronymic(patronymic)
                .login(login).uuid(UUID.fromString("e2af9c66-5ae3-11ee-8c99-0242ac120002"))
                .employmentDate(new Date()).build();
        Map<UserId, User> usersMap = Collections.singletonMap(user.getId(), user);
        when(_userService.getUsersById(anyCollectionOf(UserId.class))).thenReturn(usersMap);
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
