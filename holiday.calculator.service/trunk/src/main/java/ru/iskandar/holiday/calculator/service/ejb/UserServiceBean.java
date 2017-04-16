package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import ru.iskandar.holiday.calculator.service.entities.UserJPA;
import ru.iskandar.holiday.calculator.service.entities.UserJPA_;
import ru.iskandar.holiday.calculator.service.model.JPABasedUserFactory;
import ru.iskandar.holiday.calculator.service.model.User;

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
		CriteriaQuery<UserJPA> cq = cb.createQuery(UserJPA.class);
		Root<UserJPA> from = cq.from(UserJPA.class);

		Predicate predicate = cb.equal(from.get(UserJPA_._login), login);

		cq.where(predicate);
		cq.select(from);
		TypedQuery<UserJPA> q = _em.createQuery(cq);
		UserJPA entity;
		try {
			entity = q.getSingleResult();
		} catch (NoResultException e) {
			throw new IllegalStateException(String.format("Пользователь с логином %s не найден", login));
		}
		User user = new JPABasedUserFactory(entity).create();

		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<User> getAllUsers() {

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<UserJPA> cq = cb.createQuery(UserJPA.class);
		Root<UserJPA> from = cq.from(UserJPA.class);
		cq.select(from);
		TypedQuery<UserJPA> q = _em.createQuery(cq);
		List<UserJPA> result = q.getResultList();

		List<User> allUsers = new ArrayList<>();
		for (UserJPA entity : result) {
			User user = new JPABasedUserFactory(entity).create();
			allUsers.add(user);
		}

		return allUsers;
	}

}
