package ru.iskandar.holiday.calculator.web.service;

import java.util.Properties;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal;
import ru.iskandar.holiday.calculator.service.ejb.IUserService;
import ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorService;
import ru.iskandar.holiday.calculator.service.model.user.User;

@Path("/")
@Stateless
public class HolidayCalculatorWebService {
	@EJB
	private IHolidayCalculatorLocal _holidayService;
	@EJB
	private IUserServiceLocal _userService;

	/** JNDI имя */
	private static final String HOLIDAY_CALCULATOR_JNDI_NAME = "java:app/holiday-calculator-web-service/HolidayCalculatorBean!ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal";

	/** JNDI имя */
	private static final String USER_SERVICE_JNDI_NAME = "java:app/holiday-calculator-web-service/UserServiceBean!ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal";

	/** JNDI имя */
	// private static final String REMOTE_USER_SERVICE_JNDI_NAME =
	// "java:app/holiday-calculator-web-service/UserServiceBean!ru.iskandar.holiday.calculator.service.ejb.IUserServiceRemote";

	private IHolidayCalculatorService lookupHolidayCalculatorService() throws NamingException {
		// return _service;
		return InitialContext.doLookup(HOLIDAY_CALCULATOR_JNDI_NAME);
	}

	private IUserService lookupUserServiceLocal() throws NamingException {
		InitialContext context = createInitialContext("user1", "password1");
		return (IUserServiceLocal) context.lookup(USER_SERVICE_JNDI_NAME);
	}

	private InitialContext createInitialContext(String aUser, String aPassword) throws NamingException {
		Properties props = new Properties();
		props.put(Context.SECURITY_PRINCIPAL, aUser);
		props.put(Context.SECURITY_CREDENTIALS, aPassword);
		InitialContext context = new InitialContext(props);
		return context;
	}

	@GET
	@Path("/user/{login}/{password}")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public User getUser(@PathParam("login") String login, @PathParam("password") String password) {
		User user = _userService.findUserByLogin(login);
		System.out.println("currentUser " + user);
		return user;
	}

	/**
	 * Возвращает количество отгулов у текущего пользователя
	 *
	 * @param aUser
	 *            пользователь
	 * @return количество отгулов
	 *
	 */
	@GET
	@Path("/HolidaysQuantity/{login}/{password}")
	@Produces({ "application/json" })
	public int getHolidaysQuantity(@PathParam("login") String login, @PathParam("password") String password) {
		User user = getUser(login, password);
		return _holidayService.getHolidaysQuantity(user);
	}

}
