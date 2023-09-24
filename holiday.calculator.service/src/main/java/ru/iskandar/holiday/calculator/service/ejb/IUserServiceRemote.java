package ru.iskandar.holiday.calculator.service.ejb;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Сервис работы с пользователями
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface IUserServiceRemote extends IUserService {

    /** JNDI имя */
    public static String JNDI_NAME =
            "java:global/holiday/holiday.calculator.service/UserServiceBean!ru.iskandar.holiday.calculator.service.ejb.IUserServiceRemote";

    @GET
    @Path("/user")
    @HCWebMethod(errMess = "Ошибка получения текущего пользователя.")
    @Override
    User getCurrentUser();

    @POST
    @Path("createUser")
    @HCWebMethod(errMess = "Ошибка создания пользователя.")
    @Consumes(MediaType.APPLICATION_JSON)
    default User createUser(NewUserEntry aNewUserEntry) {
        return createUser(aNewUserEntry, new HashSet<>());
    }

    @Override
    User createUser(NewUserEntry aNewUserEntry, Set<PermissionId> aPermissions);

}

