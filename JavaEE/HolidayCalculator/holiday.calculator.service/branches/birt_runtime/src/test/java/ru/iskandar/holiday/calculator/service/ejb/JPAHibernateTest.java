package ru.iskandar.holiday.calculator.service.ejb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class JPAHibernateTest {

	protected static EntityManagerFactory emf;
	protected static EntityManager em;

	@BeforeClass
	public static void init() throws FileNotFoundException, SQLException {
		emf = Persistence.createEntityManagerFactory("HolidayCalculator-test");
		em = emf.createEntityManager();
	}

	@Before
	public void initializeDatabase() {
		Session session = em.unwrap(Session.class);
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(getClass().getResourceAsStream("/data.sql")));
					String line;
					do {
						line = br.readLine();
					} while (line != null);
					RunScript.execute(connection, br);
				} catch (FileNotFoundException e) {
					throw new RuntimeException("could not initialize with script", e);
				} catch (IOException e) {
					throw new RuntimeException("could not initialize with script", e);
				}
			}
		});
	}

	@AfterClass
	public static void tearDown() {
		em.clear();
		em.close();
		emf.close();
	}

	protected EntityManager getEntityManager() {
		return em;
	}
}
