package ru.iskandar.holiday.calculator.web.service;

import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorService;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserFactory;

@Path("/")
@Stateless
public class HolidayCalculatorWebService {
	// @EJB
	// private IHolidayCalculatorLocal _service;

	/** JNDI имя */
	private static final String HOLIDAY_CALCULATOR_JNDI_NAME = "java:app/holiday-calculator-web-service/HolidayCalculatorBean!ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal";

	/** JNDI имя */
	private static final String USER_SERVICE_JNDI_NAME = "java:app/holiday-calculator-web-service/UserServiceBean!ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal";

	private IHolidayCalculatorService lookupHolidayCalculatorService() throws NamingException {
		// return _service;
		return InitialContext.doLookup(HOLIDAY_CALCULATOR_JNDI_NAME);
	}

	private IUserServiceLocal lookupUserServiceLocal() throws NamingException {
		return InitialContext.doLookup(USER_SERVICE_JNDI_NAME);
	}

	private class CurrentUserInfoFactory extends UserFactory {

		@Override
		protected UUID getUUID() {
			return UUID.randomUUID();
		}

		@Override
		protected String getFirstName() {
			return "Олег";
		}

		@Override
		protected String getLastName() {
			return "Сидоров";
		}

		@Override
		protected String getPatronymic() {
			return "Михайлович";
		}

		@Override
		protected Date getEmploymentDate() {
			return new Date();
		}

		@Override
		protected String getLogin() {
			return "user1";
		}

	}

	@GET
	@Path("/user")
	@Produces({ "application/json" })
	public User getUser() {
		IHolidayCalculatorService service;
		try {
			service = lookupHolidayCalculatorService();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			service = null;
		}
		System.out.println("service " + service);

		IUserServiceLocal userService;
		try {
			userService = lookupUserServiceLocal();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userService = null;
		}
		System.out.println("userService " + userService);

		return new CurrentUserInfoFactory().create();
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
	@Path("/HolidaysQuantity")
	@Produces({ "application/json" })
	public int getHolidaysQuantity() {
		return 3;
	}

}
