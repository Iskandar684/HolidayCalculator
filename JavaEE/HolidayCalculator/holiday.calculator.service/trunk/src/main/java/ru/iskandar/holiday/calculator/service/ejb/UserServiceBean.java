package ru.iskandar.holiday.calculator.service.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
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

import org.jboss.logging.Logger;

import ru.iskandar.holiday.calculator.service.ejb.search.ISearchServiceLocal;
import ru.iskandar.holiday.calculator.service.ejb.search.SearchServiceException;
import ru.iskandar.holiday.calculator.service.model.user.EntityBasedUserFactory;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntityFactory;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserByIdNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity_;
import ru.iskandar.holiday.calculator.service.model.user.UserId;

/**
 * Сервис работы с пользователями
 *
 */
@Stateless
@Local(IUserServiceLocal.class)
@Remote(IUserServiceRemote.class)
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

	/**
	 * Конструктор
	 */
	public UserServiceBean() {
		_userPermissionsManager = new UserPermissionsManager();
	}

	/**
	 * Конструктор
	 */
	UserServiceBean(EntityManager aEntityManager, ISearchServiceLocal aSearchServiceLocal,
			IUserPermissionsManager aUserPermissionsManager) {
		_em = Objects.requireNonNull(aEntityManager);
		_searchServiceLocal = Objects.requireNonNull(aSearchServiceLocal);
		_userPermissionsManager = Objects.requireNonNull(aUserPermissionsManager);
	}

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

	@Override
	public User createUser(NewUserEntry aNewUserEntry, Set<PermissionId> aPermissions) {
		Objects.requireNonNull(aNewUserEntry);
		UserEntity entity = new NewUserEntityFactory(aNewUserEntry).create();
		_em.persist(entity);
		try {
			_userPermissionsManager.addOrChangePermissions(aNewUserEntry.getLogin(), aNewUserEntry.getPassword(),
					aPermissions);
		} catch (HolidayCalculatorException e) {
			LOG.error(e.getMessage(), e);
		}
		User user = new EntityBasedUserFactory(entity).create();
		try {
			_searchServiceLocal.addOrUpdate(user);
		} catch (SearchServiceException e) {
			LOG.error(e.getMessage(), e);
		}
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

	private UserEntity findUser(UserId aId) {
		Objects.requireNonNull(aId);

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
			throw new UserByIdNotFoundException(String.format("Пользователь с id=%s не найден.", aId), e);
		}
		return entity;
	}

	@Override
	public void changeNote(UserId aUserId, String aNewNote) {
		UserEntity userEntity = findUser(aUserId);
		userEntity.setNote(aNewNote);
		User user = new EntityBasedUserFactory(userEntity).create();
		try {
			_searchServiceLocal.addOrUpdate(user);
		} catch (SearchServiceException e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
