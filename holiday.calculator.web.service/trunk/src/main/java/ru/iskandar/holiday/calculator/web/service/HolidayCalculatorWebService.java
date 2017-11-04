package ru.iskandar.holiday.calculator.web.service;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorBean;
import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal;
import ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.permissions.Permission;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

@Path("/")
@Stateless
public class HolidayCalculatorWebService {

	/** Логгер */
	private static final Logger LOG = Logger.getLogger(HolidayCalculatorBean.class);

	@EJB
	private IHolidayCalculatorLocal _holidayService;

	@EJB
	private IUserServiceLocal _userService;

	@Context
	private HttpServletRequest _request;

	@GET
	@Path("/login/{username}/{password}")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public boolean login(@PathParam("username") String username, @PathParam("password") String password) {
		System.out.println("login request " + _request);
		try {
			_request.login(username, password);
			System.out.println("login OK");
			String authType = _request.getAuthType();
			System.out.println("authType  " + authType);
			System.out.println("getRemoteUser " + _request.getRemoteUser());
			System.out.println("getUserPrincipal " + _request.getUserPrincipal());
			System.out.println("isUserInRole CONSIDER " + _request.isUserInRole(Permission.CONSIDER));
			return true;
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("login FAIL");
			return false;
		}
	}

	@GET
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public void logout() {
		System.out.println("logout");
		try {
			_request.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GET
	@Path("/isLoggedIn")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public boolean isLoggedIn() {
		System.out.println("isLoggedIn  UserPrincipal " + _request.getUserPrincipal());
		Principal principal = _request.getUserPrincipal();
		return (principal != null) && !"anonymous".equalsIgnoreCase(principal.getName());
	}

	@GET
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public User getUser() {
		System.out.println("getUser  UserPrincipal " + _request.getUserPrincipal());
		User user = _userService.getCurrentUser();
		System.out.println("getUser " + user);
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
	@Path("/HolidaysQuantity")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public int getHolidaysQuantity() {
		System.out.println("getHolidaysQuantity  UserPrincipal " + _request.getUserPrincipal());
		User user = _userService.getCurrentUser();
		return _holidayService.getHolidaysQuantity(user);
	}

	@GET
	@Path("/takeHoliday/{dates}")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public Response takeHoliday(@PathParam("dates") Date[] aDates) {
		if (!isLoggedIn()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Objects.requireNonNull(aDates);
		User user = _userService.getCurrentUser();
		Set<Date> dates = new HashSet<Date>();
		for (Date date : aDates) {
			dates.add(date);
		}
		HolidayStatementEntry entry = new HolidayStatementEntry(dates, user);
		try {
			_holidayService.createHolidayStatement(entry);
		} catch (StatementAlreadySendedException e) {
			LOG.error("Аналогичное заявление уже было отправлено", e);
			return Response.status(Status.CONFLICT).build();
		}
		return Response.ok().build();
	}

}
