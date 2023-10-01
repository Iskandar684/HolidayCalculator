package ru.iskandar.holiday.calculator.user.service.ejb;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.NonNull;
import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;
import ru.iskandar.holiday.calculator.user.service.entity.NewUserEntityFactory;
import ru.iskandar.holiday.calculator.user.service.entity.UserEntity;
import ru.iskandar.holiday.calculator.user.service.entity.UserEntity_;
import ru.iskandar.holiday.calculator.user.service.model.EntityBasedUserFactory;

/**
 * Сервис работы с пользователями.
 */
@Stateless
@Remote(IUserServiceRemote.class)
public class UserServiceBean implements IUserServiceRemote {

    /** Менеджер сущностей */
    @PersistenceContext
    private EntityManager _em;

    @Override
    public Collection<User> getUsersById(@NonNull UUID[] aUUIDs) {
        if (aUUIDs.length == 0) {
            return Collections.emptySet();
        }
        CriteriaBuilder cb = _em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);
        cq.select(from);
        Predicate loginEqual = from.get(UserEntity_.uuid).in(Arrays.asList(aUUIDs));
        cq.where(loginEqual);

        TypedQuery<UserEntity> q = _em.createQuery(cq);
        return q.getResultList().stream().map(this::toUser).collect(Collectors.toList());
    }

    /**
     * Создаёт доменный объект пользователя из сущности пользователя.
     *
     * @param aEntity сущность пользователя
     * @return доменный объект пользователя
     */
    private User toUser(UserEntity aEntity) {
        return new EntityBasedUserFactory(aEntity).create();
    }

    @Override
    public Collection<User> getAllUsers() {
        CriteriaBuilder cb = _em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);
        cq.select(from);
        TypedQuery<UserEntity> q = _em.createQuery(cq);
        List<UserEntity> result = q.getResultList();
        return result.stream().map(this::toUser).collect(Collectors.toList());
    }

    @Override
    public User createUser(@NonNull NewUserEntry aNewUserEntry) {
        validateNewUser(aNewUserEntry);
        UserEntity entity = new NewUserEntityFactory(aNewUserEntry).create();
        _em.persist(entity);
        return toUser(entity);
    }

    /**
     * Валидирует сущность создаваемого пользователя.
     *
     * @param aNewUserEntry сущность пользователя
     */
    private void validateNewUser(@NonNull NewUserEntry aNewUserEntry) {
        if (aNewUserEntry.getLogin() == null || aNewUserEntry.getLogin().isEmpty()) {
            throw new IllegalArgumentException("Не указан логин.");
        }
        if (aNewUserEntry.getFirstName() == null || aNewUserEntry.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Не указано имя.");
        }
        if (aNewUserEntry.getLastName() == null || aNewUserEntry.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Не указана фамилия.");
        }
        if (aNewUserEntry.getPatronymic() == null || aNewUserEntry.getPatronymic().isEmpty()) {
            throw new IllegalArgumentException("Не указано отчество.");
        }
        if (aNewUserEntry.getPassword() == null || aNewUserEntry.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Не указан пароль.");
        }
    }

    @Override
    public User findUserByLogin(@NonNull String aLogin) {
        CriteriaBuilder cb = _em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);
        cq.select(from);

        Predicate loginEqual = cb.equal(from.get(UserEntity_.login), aLogin);
        cq.where(loginEqual);

        TypedQuery<UserEntity> q = _em.createQuery(cq);
        UserEntity entity;
        try {
            entity = q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return toUser(entity);
    }

    /**
     * Ищет пользователя по идентификатору.
     *
     * @param aId идентификатор
     * @return пользователя или {@code null}, если пользователь не найден
     */
    private UserEntity findUser(@NonNull UserId aId) {
        CriteriaBuilder cb = _em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> from = cq.from(UserEntity.class);
        cq.select(from);

        Predicate loginEqual = cb.equal(from.get(UserEntity_.uuid), aId.getUUID());
        cq.where(loginEqual);

        TypedQuery<UserEntity> q = _em.createQuery(cq);
        UserEntity entity;
        try {
            entity = q.getSingleResult();
        } catch (NoResultException e) {
            throw new UserByIdNotFoundException(
                    String.format("Пользователь с id=%s не найден.", aId), e);
        }
        return entity;
    }

    @Override
    public User changeNote(@NonNull UUID aUserId, @NonNull String aNewNote) {
        UserEntity userEntity = findUser(UserId.fromString(aUserId));
        userEntity.setNote(aNewNote);
        _em.merge(userEntity);
        return toUser(userEntity);
    }

}
