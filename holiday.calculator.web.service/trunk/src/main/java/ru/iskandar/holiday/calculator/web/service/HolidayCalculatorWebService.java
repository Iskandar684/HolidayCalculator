package ru.iskandar.holiday.calculator.web.service;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal;
import ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal;
import ru.iskandar.holiday.calculator.service.model.permissions.Permission;
import ru.iskandar.holiday.calculator.service.model.user.User;

@Path("/")
@Stateless
@DeclareRoles({ Permission.CONSIDER, Permission.USER_CREATOR, Permission.USER_VIEWER })
public class HolidayCalculatorWebService {

	@EJB
	private IHolidayCalculatorLocal _holidayService;

	@EJB
	private IUserServiceLocal _userService;

	@Context
	private HttpServletRequest _request;

	@GET
	@Path("/login/{login}/{password}")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public boolean login(@PathParam("login") String login, @PathParam("password") String password) {
		System.out.println("login request " + _request);
		try {
			_request.login(login, password);
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
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed(Permission.USER_VIEWER)
	public User getUser() {
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

}
