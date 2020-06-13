package ru.iskandar.holiday.calculator.service.ejb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Сервис работы с пользователями
 */
@Path("/")
@Produces({MediaType.APPLICATION_JSON})
public interface IUserServiceRemote extends IUserService {

    /** JNDI имя */
    public static String JNDI_NAME =
            "java:global/holiday/holiday.calculator.service/UserServiceBean!ru.iskandar.holiday.calculator.service.ejb.IUserServiceRemote";

    @GET
    @Path("/user")
    @HCWebMethod(errMess = "Ошибка получения текущего пользователя.")
    @Override
    User getCurrentUser();

}
