package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Интерфейс сервиса пользователей.
 */
@RegisterRestClient
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public interface IUserServiceClient {

    /**
     * Создает нового пользователя.
     *
     * @param aNewUserEntry описание создаваемого пользователя
     * @return созданный пользователь
     */
    @POST
    @Path("createUser")
    @HCWebMethod(errMess = "Ошибка создания пользователя.")
    @Consumes(MediaType.APPLICATION_JSON)
    User createUser(NewUserEntry aNewUserEntry);

    /**
     * Меняет примечание пользователя.
     *
     * @param aUserId идентификатор пользователя
     * @param aNewNote новое примечание
     * @return пользователя
     */
    @PUT
    @Path("changeNote/{uuid}/{note}")
    @HCWebMethod(errMess = "Ошибка изменения примечания пользователя.")
    User changeNote(@PathParam("uuid") UUID aUserUUID, @PathParam("note") String aNewNote);

    /**
     * Ищет пользователя с указанным логином.
     *
     * @param aLogin логин
     * @return найденный пользователь или {@code null}, если пользователь с
     *         указанным логином не существует
     */
    @GET
    @Path("userByLogin/{login}")
    @HCWebMethod(errMess = "Ошибка получения пользователя по логину.")
    User findUserByLogin(@PathParam("login") String aLogin);

    /**
     * Возвращает пользователей по их идентификаторам.
     *
     * @param aUUIDs идентификаторы пользователей
     * @return пользователей
     */
    @GET
    @Path("users")
    @HCWebMethod(errMess = "Ошибка получения пользователей по их идентификаторам.")
    Collection<User> getUsersById(@QueryParam("uuids") UUID[] aUUIDs);

    /**
     * Возвращает всех пользователей.
     *
     * @return всех пользователей
     */
    @GET
    @Path("allUsers")
    @HCWebMethod(errMess = "Ошибка получения всех пользователей.")
    Collection<User> getAllUsers();

}
