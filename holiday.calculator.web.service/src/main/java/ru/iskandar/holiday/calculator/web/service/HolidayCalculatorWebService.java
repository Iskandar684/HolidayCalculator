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
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import ru.iskandar.holiday.calculator.service.model.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.document.DocumentPreviewException;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;
import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Веб-сервис учета отгулов
 *
 * @author Искандар
 */
@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
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
    public User login(@PathParam("username") String username,
            @PathParam("password") String password) {
        User user = _userService.findUserByLogin(username);
        if (user == null) {
            throw new UserByLoginNotFoundException(
                    String.format("Пользователь с логином %s не найден.", username));
        }
        try {
            _request.login(username, password);
            LOG.info(String.format("Успешный вход в систему [login=%s].", username));
        } catch (ServletException e) {
            throw new LoginException("Неверный логин или пароль.", e);
        }
        _holidayService.checkAuthentification();
        return user;
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

    /**
     * Возвращает входящие заявления.
     *
     * @return входящие заявления
     */
    @GET
    @Path("/incomingStatements")
    @PermitAll
    public Statement<?>[] getIncomingStatements() {
        if (!_holidayService.canConsider()) {
            throw new EJBAccessException(String.format(
                    "У текущего пользователя (%s) нет прав на рассмотрение заявлений.",
                    _request.getUserPrincipal()));
        }
        return _holidayService.getIncomingStatements().toArray(new Statement<?>[0]);
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

    @POST
    @Path("/approve/{statementUUID}")
    @PermitAll
    public Statement<?> approve(@PathParam("statementUUID") String statementUUID)
            throws StatementAlreadyConsideredException {
        StatementId statementID = StatementId.from(UUID.fromString(statementUUID));
        return _holidayService.approve(statementID);
    }

    @POST
    @Path("/reject/{statementUUID}")
    @PermitAll
    public Statement<?> reject(@PathParam("statementUUID") String statementUUID)
            throws StatementAlreadyConsideredException {
        StatementId statementID = StatementId.from(UUID.fromString(statementUUID));
        return _holidayService.reject(statementID);
    }

    @GET
    @Path("/canConsider")
    public boolean canConsider() {
        return _holidayService.canConsider();
    }

    @GET
    @Path("/canCreateUser")
    public boolean canCreateUser() {
        return _holidayService.canCreateUser();
    }

    @GET
    @Path("/userHolidaysInfo")
    @PermitAll
    public UserHolidaysInfo getUserHolidaysInfo() {
        User user = _userService.getCurrentUser();
        return UserHolidaysInfo.builder().userUUID(user.getUuid())
                .holidaysQuantity(_holidayService.getHolidaysQuantity(user))
                .incomingHolidaysQuantity(_holidayService.getIncomingHolidaysQuantity(user))
                .leaveCount(_holidayService.getLeaveCount(user))
                .nextLeaveStartDate(_holidayService.getNextLeaveStartDate(user))
                .outgoingHolidaysQuantity(_holidayService.getOutgoingHolidaysQuantity(user))
                .outgoingLeaveCount(_holidayService.getOutgoingLeaveCount(user)).build();
    }

}
