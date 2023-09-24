package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import ru.iskandar.holiday.calculator.service.ejb.search.ISearchServiceLocal;
import ru.iskandar.holiday.calculator.service.ejb.search.SearchServiceException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;
import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сервис работы с пользователями
 */
@Stateless
@Local(IUserServiceLocal.class)
@Remote(IUserServiceRemote.class)
@AllArgsConstructor
public class UserServiceBean implements IUserServiceLocal, IUserServiceRemote {

    /** Логгер */
    private static final Logger LOG = Logger.getLogger(UserServiceBean.class.getName());

    /** Менеджер сущностей */
    @PersistenceContext
    private EntityManager _em;

    /** Сервис поиска */
    @EJB
    private ISearchServiceLocal _searchServiceLocal;

    /** Менеджер управления полномочиями пользователей */
    private final IUserPermissionsManager _userPermissionsManager;

    /** Контекст сессии */
    @Resource
    private SessionContext _context;

    @Inject
    private IUserServiceClient _userServiceClient;

    /**
     * Конструктор
     */
    public UserServiceBean() {
        _userPermissionsManager = new UserPermissionsManager();
    }

    @Override
    @PermitAll
    public User getCurrentUser() {
        Principal principal = _context.getCallerPrincipal();
        String login = principal.getName();
        User user = _userServiceClient.findUserByLogin(login);
        if (user == null) {
            throw new UserByLoginNotFoundException(
                    String.format("Пользователь с логином %s не найден", login));
        }
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        return _userServiceClient.getAllUsers();
    }

    @Override
    public User createUser(@NonNull NewUserEntry aNewUserEntry,
            @NonNull Set<PermissionId> aPermissions) {
        User user = _userServiceClient.createUser(aNewUserEntry);
        try {
            _userPermissionsManager.addOrChangePermissions(aNewUserEntry.getLogin(),
                    aNewUserEntry.getPassword(), aPermissions);
        } catch (HolidayCalculatorException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            _searchServiceLocal.addOrUpdate(user);
        } catch (SearchServiceException e) {
            String mess =
                    String.format("Ошибка добавления созданного пользователя [%s] в Elastic Search",
                            aNewUserEntry);
            LOG.debug(mess, e);
            LOG.warn(mess + ": " + e.getLocalizedMessage());
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUserByLogin(@NonNull String aLogin) {
        return _userServiceClient.findUserByLogin(aLogin);
    }

    @Override
    public void changeNote(@NonNull UserId aUserId, @NonNull String aNewNote) {
        User user = _userServiceClient.changeNote(aUserId.getUUID(), aNewNote);
        try {
            _searchServiceLocal.addOrUpdate(user);
        } catch (SearchServiceException e) {
            String mess = String.format(
                    "Ошибка добавления примечания пользователя [%s] в Elastic Search", aUserId);
            LOG.debug(mess, e);
            LOG.warn(mess + ": " + e.getLocalizedMessage());
        }
    }

    @Override
    public Map<UserId, User> getUsersById(@NonNull Collection<UserId> aIds) {
        Collection<User> users = _userServiceClient
                .getUsersById(aIds.stream().map(UserId::getUUID).toArray(UUID[]::new));
        return users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

}
