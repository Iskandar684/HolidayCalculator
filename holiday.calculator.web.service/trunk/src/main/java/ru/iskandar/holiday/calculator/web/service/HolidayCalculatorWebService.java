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
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Веб-сервис учета отгулов
 *
 * @author Искандар
 *
 */
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
		try {
			_request.login(username, password);
			LOG.info(String.format("Успешный вход в систему [login=%s].", username));
			return true;
		} catch (ServletException e) {
			LOG.error(String.format("Ошибка входа в систему [login=%s].", username), e);
			return false;
		}
	}

	@GET
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public void logout() {
		try {
			_request.logout();
			LOG.info(String.format("Успешный выход из системы [login=%s].", _request.getUserPrincipal()));
		} catch (ServletException e) {
			LOG.error(String.format("Ошибка выхода из системы [login=%s].", _request.getUserPrincipal()), e);
		}
	}

	@GET
	@Path("/isLoggedIn")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public boolean isLoggedIn() {
		Principal principal = _request.getUserPrincipal();
		return (principal != null) && !"anonymous".equalsIgnoreCase(principal.getName());
	}

	@GET
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public User getUser() {
		User user = _userService.getCurrentUser();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUser  UserPrincipal " + _request.getUserPrincipal());
		}
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
		User user = _userService.getCurrentUser();
		return _holidayService.getHolidaysQuantity(user);
	}

	/**
	 * Возвращает количество исходящих дней отгула. Это количество дней, на
	 * которое уменьшется количество общее дней отгула, после того как заявление
	 * на отгул будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отгула
	 */
	@GET
	@Path("/OutgoingHolidaysQuantity")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public int getOutgoingHolidaysQuantity() {
		User user = _userService.getCurrentUser();
		return _holidayService.getOutgoingHolidaysQuantity(user);
	}

	/**
	 * Возвращает количество приходящих отгулов. Это количество дней, на которое
	 * будет увеличино общее количество отгулов, после того как засчитают отзыв.
	 *
	 * @return количество приходящих отгулов
	 */
	@GET
	@Path("/IncomingHolidaysQuantity")
	@Produces({ MediaType.APPLICATION_JSON })
	@PermitAll
	public int getIncomingHolidaysQuantity() {
		User user = _userService.getCurrentUser();
		return _holidayService.getIncomingHolidaysQuantity(user);
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