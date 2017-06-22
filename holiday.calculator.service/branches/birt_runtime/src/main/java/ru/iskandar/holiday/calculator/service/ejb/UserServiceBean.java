package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ru.iskandar.holiday.calculator.service.model.user.EntityBasedUserFactory;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntityFactory;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity_;

/**
 * Сервис работы с пользователями
 *
 */
@Stateless
@Local(IUserServiceLocal.class)
public class UserServiceBean implements IUserServiceLocal {

	/** Менеджер сущностей */
	@PersistenceContext
	private EntityManager _em;

	/**
	 * Конструктор
	 */
	public UserServiceBean() {
	}

	/**
	 * Конструктор
	 */
	UserServiceBean(EntityManager aEntityManager) {
		Objects.requireNonNull(aEntityManager);
		_em = aEntityManager;
	}

	/** Контекст сессии */
	@Resource
	private SessionContext _context;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PermitAll
	public User getCurrentUser() {

		Principal principal = _context.getCallerPrincipal();
		String login = principal.getName();

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		Root<UserEntity> from = cq.from(UserEntity.class);

		Predicate predicate = cb.equal(from.get(UserEntity_.login), login);

		cq.where(predicate);
		cq.select(from);
		TypedQuery<UserEntity> q = _em.createQuery(cq);
		UserEntity entity;
		try {
			entity = q.getSingleResult();
		} catch (NoResultException e) {
			throw new UserByLoginNotFoundException(String.format("Пользователь с логином %s не найден", login));
		}
		User user = new EntityBasedUserFactory(entity).create();

		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<User> getAllUsers() {

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		Root<UserEntity> from = cq.from(UserEntity.class);
		cq.select(from);
		TypedQuery<UserEntity> q = _em.createQuery(cq);
		List<UserEntity> result = q.getResultList();

		List<User> allUsers = new ArrayList<>();
		for (UserEntity entity : result) {
			User user = new EntityBasedUserFactory(entity).create();
			allUsers.add(user);
		}

		return allUsers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User createUser(NewUserEntry aNewUserEntry) {
		Objects.requireNonNull(aNewUserEntry);
		UserEntity entity = new NewUserEntityFactory(aNewUserEntry).create();
		_em.persist(entity);
		User user = new EntityBasedUserFactory(entity).create();
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserByLogin(String aLogin) {
		Objects.requireNonNull(aLogin);

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
		User user = new EntityBasedUserFactory(entity).create();
		return user;
	}

}