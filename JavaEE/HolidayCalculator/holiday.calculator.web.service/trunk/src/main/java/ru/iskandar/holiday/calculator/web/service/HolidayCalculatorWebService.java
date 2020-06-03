package ru.iskandar.holiday.calculator.web.service;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorLocal;
import ru.iskandar.holiday.calculator.service.ejb.IUserServiceLocal;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.document.DocumentPreviewException;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;

/**
 * Веб-сервис учета отгулов
 *
 * @author Искандар
 */
@Path("/")
@Stateless
public class HolidayCalculatorWebService {

    /** Логгер */
    private static final Logger LOG = Logger.getLogger(HolidayCalculatorWebService.class);

    @EJB
    private IHolidayCalculatorLocal _holidayService;

    @EJB
    private IUserServiceLocal _userService;

    @Context
    private HttpServletRequest _request;

    @GET
    @Path("/login/{username}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public boolean login(@PathParam("username") String username,
            @PathParam("password") String password) {
        try {
            _request.login(username, password);
            LOG.info(String.format("Успешный вход в систему [login=%s].", username));
        } catch (ServletException e) {
            LOG.error(String.format("Ошибка входа в систему [login=%s].", username), e);
            return false;
        }
        try {
            _holidayService.checkAuthentification();
        } catch (UserByLoginNotFoundException e) {
            LOG.error(String.format(
                    "Ошибка входа в систему: для логина [login=%s] пользователь не найден.",
                    username), e);
            return false;
        }
        return true;
    }

    @GET
    @Path("/logout")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public void logout() {
        try {
            _request.logout();
            LOG.info(String.format("Успешный выход из системы [login=%s].",
                    _request.getUserPrincipal()));
        } catch (ServletException e) {
            LOG.error(String.format("Ошибка выхода из системы [login=%s].",
                    _request.getUserPrincipal()), e);
        }
    }

    @GET
    @Path("/isLoggedIn")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public boolean isLoggedIn() {
        Principal principal = _request.getUserPrincipal();
        boolean logged = (principal != null) && !"anonymous".equalsIgnoreCase(principal.getName());
        LOG.info("isLoggedIn=" + principal + " logged=" + logged);
        return logged;
    }

    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    @HCWebMethod(errMess = "Ошибка получения текущего пользователя.")
    public User getUser() {
        User user = _userService.getCurrentUser();
        if (LOG.isDebugEnabled()) {
            LOG.debug("getUser UserPrincipal " + _request.getUserPrincipal());
        }
        return user;
    }

    /**
     * Возвращает количество отгулов у текущего пользователя
     *
     * @param aUser пользователь
     * @return количество отгулов
     */
    @GET
    @Path("/HolidaysQuantity")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public int getHolidaysQuantity() {
        User user = _userService.getCurrentUser();
        return _holidayService.getHolidaysQuantity(user);
    }

    /**
     * Возвращает количество исходящих дней отгула. Это количество дней, на которое уменьшется
     * количество общее дней отгула, после того как заявление на отгул будет принят.
     *
     * @return не отрицательное количество исходящих дней отгула
     */
    @GET
    @Path("/OutgoingHolidaysQuantity")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public int getOutgoingHolidaysQuantity() {
        User user = _userService.getCurrentUser();
        return _holidayService.getOutgoingHolidaysQuantity(user);
    }

    /**
     * Возвращает количество приходящих отгулов. Это количество дней, на которое будет увеличино
     * общее количество отгулов, после того как засчитают отзыв.
     *
     * @return количество приходящих отгулов
     */
    @GET
    @Path("/IncomingHolidaysQuantity")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public int getIncomingHolidaysQuantity() {
        User user = _userService.getCurrentUser();
        return _holidayService.getIncomingHolidaysQuantity(user);
    }

    @GET
    @Path("/takeHoliday/{dates}")
    @Produces({MediaType.APPLICATION_JSON})
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

    /**
     * Возвращает количество неиспользованных дней отпуска
     *
     * @return количество дней
     */
    @GET
    @Path("/LeaveCount")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public int getLeaveCount() {
        User user = _userService.getCurrentUser();
        return _holidayService.getLeaveCount(user);
    }

    /**
     * Возвращает количество исходящих дней отпуска. Это количество дней, на которое уменьшется
     * количество дней отпуска в этом периоде, после того как заявление на отпуск будет принят.
     *
     * @return не отрицательное количество исходящих дней отпуска.
     */
    @GET
    @Path("/OutgoingLeaveCount")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public int getOutgoingLeaveCount() {
        User user = _userService.getCurrentUser();
        return _holidayService.getOutgoingLeaveCount(user);
    }

    /**
     * Возвращает дату начала следующего периода
     *
     * @return дата начала следующего периода
     */
    @GET
    @Path("/NextLeaveStartDate")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public Date getNextLeaveStartDate() {
        User user = _userService.getCurrentUser();
        return _holidayService.getNextLeaveStartDate(user);
    }

    /**
     * Возвращает все заявления текущего пользователя
     *
     * @return заявления
     */
    @GET
    @Path("/currentUserStatements")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public Statement<?>[] getAllStatements() {
        User user = _userService.getCurrentUser();
        return _holidayService.getAllStatements(user).toArray(new Statement<?>[0]);
    }

    /**
     * Формирует документ заявления на отгул
     *
     * @param aStatementID идентификатор заявления
     * @return документ заявления
     * @throws DocumentPreviewException если не удалось сформировать документ
     */
    @GET
    @Path("/getStatementDocument/{statementUUID}")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public HTMLDocument getStatementDocument(@PathParam("statementUUID") String statementUUID) {
        StatementId statementID = StatementId.from(UUID.fromString(statementUUID));
        StatementDocument document;
        try {
            document = _holidayService.getStatementDocument(statementID);
        } catch (DocumentPreviewException e) {
            throw new IllegalStateException(
                    String.format("Ошибка получения документа заявления с ID=%s", statementUUID),
                    e);
        }
        return new HTMLDocument(new String(document.getContent(), Charset.forName("utf-8")));
    }

}
