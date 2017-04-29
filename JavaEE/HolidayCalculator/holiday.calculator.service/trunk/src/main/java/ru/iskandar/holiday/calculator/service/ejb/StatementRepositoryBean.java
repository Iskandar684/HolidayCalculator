package ru.iskandar.holiday.calculator.service.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.iskandar.holiday.calculator.service.entities.DOBasedHolidayStatementEntityFactory;
import ru.iskandar.holiday.calculator.service.entities.DOBasedLeaveStatementEntityFactory;
import ru.iskandar.holiday.calculator.service.entities.DOBasedRecallStatementEntityFactory;
import ru.iskandar.holiday.calculator.service.entities.EntryBasedHolidayStatementEntityFactory;
import ru.iskandar.holiday.calculator.service.entities.EntryBasedLeaveStatementEntityFactory;
import ru.iskandar.holiday.calculator.service.entities.EntryBasedRecallStatementEntityFactory;
import ru.iskandar.holiday.calculator.service.entities.HolidayStatementEntity;
import ru.iskandar.holiday.calculator.service.entities.HolidayStatementEntity_;
import ru.iskandar.holiday.calculator.service.entities.LeaveStatementEntity;
import ru.iskandar.holiday.calculator.service.entities.LeaveStatementEntity_;
import ru.iskandar.holiday.calculator.service.entities.RecallStatementEntity;
import ru.iskandar.holiday.calculator.service.entities.RecallStatementEntity_;
import ru.iskandar.holiday.calculator.service.model.statement.EntityBasedHolidayStatementFactory;
import ru.iskandar.holiday.calculator.service.model.statement.EntityBasedLeaveStatementFactory;
import ru.iskandar.holiday.calculator.service.model.statement.EntityBasedRecallStatementFactory;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.statement.StatementType;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity;

/**
 * Бин репозитория заявлений
 */
@Stateless
@Local(IStatementRepository.class)
public class StatementRepositoryBean implements IStatementRepository {

	/** Менеджер сущностей */
	@PersistenceContext
	private EntityManager _em;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Statement<?>> getStatementsByAuthor(User aAuthor) {
		Objects.requireNonNull(aAuthor);
		Set<Statement<?>> statementsByUser = new HashSet<>();
		statementsByUser.addAll(getHolidayStatementsByAuthor(aAuthor));
		statementsByUser.addAll(getRecallStatementsByAuthor(aAuthor));
		statementsByUser.addAll(getLeaveStatementsByAuthor(aAuthor));
		return statementsByUser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<HolidayStatement> getHolidayStatementsByAuthor(User aAuthor) {
		Objects.requireNonNull(aAuthor);
		UserEntity userEntity = _em.find(UserEntity.class, aAuthor.getId().getUUID());
		if (userEntity == null) {
			return Collections.emptyList();
		}

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<HolidayStatementEntity> query = cb.createQuery(HolidayStatementEntity.class);
		Root<HolidayStatementEntity> root = query.from(HolidayStatementEntity.class);
		query.where(cb.equal(root.get(HolidayStatementEntity_.author), userEntity));
		Collection<HolidayStatementEntity> result = _em.createQuery(query).getResultList();

		List<HolidayStatement> statementsByAuthor = new ArrayList<>();
		for (HolidayStatementEntity entity : result) {
			HolidayStatement statement = new EntityBasedHolidayStatementFactory(entity).create();
			statementsByAuthor.add(statement);
		}
		return statementsByAuthor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RecallStatement> getRecallStatementsByAuthor(User aAuthor) {
		Objects.requireNonNull(aAuthor);
		UserEntity userEntity = _em.find(UserEntity.class, aAuthor.getId().getUUID());
		if (userEntity == null) {
			return Collections.emptyList();
		}

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<RecallStatementEntity> query = cb.createQuery(RecallStatementEntity.class);
		Root<RecallStatementEntity> root = query.from(RecallStatementEntity.class);
		query.where(cb.equal(root.get(RecallStatementEntity_.author), userEntity));
		Collection<RecallStatementEntity> result = _em.createQuery(query).getResultList();

		List<RecallStatement> statementsByAuthor = new ArrayList<>();
		for (RecallStatementEntity entity : result) {
			RecallStatement statement = new EntityBasedRecallStatementFactory(entity).create();
			statementsByAuthor.add(statement);
		}
		return statementsByAuthor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<LeaveStatement> getLeaveStatementsByAuthor(User aAuthor) {
		Objects.requireNonNull(aAuthor);
		UserEntity userEntity = _em.find(UserEntity.class, aAuthor.getId().getUUID());
		if (userEntity == null) {
			return Collections.emptyList();
		}

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<LeaveStatementEntity> query = cb.createQuery(LeaveStatementEntity.class);
		Root<LeaveStatementEntity> root = query.from(LeaveStatementEntity.class);
		query.where(cb.equal(root.get(LeaveStatementEntity_.author), userEntity));
		Collection<LeaveStatementEntity> result = _em.createQuery(query).getResultList();

		List<LeaveStatement> statementsByAuthor = new ArrayList<>();
		for (LeaveStatementEntity entity : result) {
			LeaveStatement statement = new EntityBasedLeaveStatementFactory(entity).create();
			statementsByAuthor.add(statement);
		}
		return statementsByAuthor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Statement<?>> getStatementsByStatus(EnumSet<StatementStatus> aStatuses) {
		Objects.requireNonNull(aStatuses);
		Set<Statement<?>> res = new HashSet<>();
		res.addAll(getHolidayStatementsByStatus(aStatuses));
		res.addAll(getLeaveStatementsByStatus(aStatuses));
		res.addAll(getRecallStatementsByStatus(aStatuses));
		return res;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayStatement getHolidayStatement(StatementId aStatementUUID) {
		Objects.requireNonNull(aStatementUUID);

		HolidayStatementEntity entity = _em.find(HolidayStatementEntity.class, aStatementUUID.getUUID());
		if (entity == null) {
			return null;
		}
		return new EntityBasedHolidayStatementFactory(entity).create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveStatement getLeaveStatement(StatementId aStatementUUID) {
		Objects.requireNonNull(aStatementUUID);

		LeaveStatementEntity entity = _em.find(LeaveStatementEntity.class, aStatementUUID.getUUID());
		if (entity == null) {
			return null;
		}
		return new EntityBasedLeaveStatementFactory(entity).create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecallStatement getRecallStatement(StatementId aStatementUUID) {
		Objects.requireNonNull(aStatementUUID);

		RecallStatementEntity entity = _em.find(RecallStatementEntity.class, aStatementUUID.getUUID());
		if (entity == null) {
			return null;
		}
		return new EntityBasedRecallStatementFactory(entity).create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement<?> getStatement(StatementId aUUID, StatementType aType) {
		Objects.requireNonNull(aUUID);
		Objects.requireNonNull(aType);

		switch (aType) {
		case HOLIDAY_STATEMENT:
			return getHolidayStatement(aUUID);

		case LEAVE_STATEMENT:
			return getLeaveStatement(aUUID);

		case RECALL_STATEMENT:
			return getRecallStatement(aUUID);

		default:
			throw new IllegalArgumentException(String.format("Тип заявления %s не поддерживается", aType));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Statement<?> aStatement) {
		Objects.requireNonNull(aStatement);
		switch (aStatement.getStatementType()) {
		case HOLIDAY_STATEMENT:
			HolidayStatementEntity holidayEntity = new DOBasedHolidayStatementEntityFactory(
					(HolidayStatement) aStatement).create();
			_em.merge(holidayEntity);
			break;

		case LEAVE_STATEMENT:
			LeaveStatementEntity leaveEntity = new DOBasedLeaveStatementEntityFactory((LeaveStatement) aStatement)
					.create();
			_em.merge(leaveEntity);
			break;

		case RECALL_STATEMENT:
			RecallStatementEntity recallEntity = new DOBasedRecallStatementEntityFactory((RecallStatement) aStatement)
					.create();
			_em.merge(recallEntity);
			break;

		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayStatement createHolidayStatement(HolidayStatementEntry aStatementEntry) {
		Objects.requireNonNull(aStatementEntry);
		HolidayStatementEntity entity = new EntryBasedHolidayStatementEntityFactory(aStatementEntry).create();
		_em.persist(entity);
		HolidayStatement statement = new EntityBasedHolidayStatementFactory(entity).create();
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveStatement createLeaveStatement(LeaveStatementEntry aStatementEntry) {
		Objects.requireNonNull(aStatementEntry);
		LeaveStatementEntity entity = new EntryBasedLeaveStatementEntityFactory(aStatementEntry).create();
		_em.persist(entity);
		LeaveStatement statement = new EntityBasedLeaveStatementFactory(entity).create();
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecallStatement createRecallStatement(RecallStatementEntry aStatementEntry) {
		Objects.requireNonNull(aStatementEntry);
		RecallStatementEntity entity = new EntryBasedRecallStatementEntityFactory(aStatementEntry).create();
		_em.persist(entity);
		RecallStatement statement = new EntityBasedRecallStatementFactory(entity).create();
		return statement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<HolidayStatement> getHolidayStatementsByStatus(EnumSet<StatementStatus> aStatuses) {
		Objects.requireNonNull(aStatuses);

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<HolidayStatementEntity> query = cb.createQuery(HolidayStatementEntity.class);
		Root<HolidayStatementEntity> root = query.from(HolidayStatementEntity.class);

		query.where(root.get(HolidayStatementEntity_.status).in(aStatuses));
		Collection<HolidayStatementEntity> result = _em.createQuery(query).getResultList();

		List<HolidayStatement> statementsByStatuses = new ArrayList<>();
		for (HolidayStatementEntity entity : result) {
			HolidayStatement statement = new EntityBasedHolidayStatementFactory(entity).create();
			statementsByStatuses.add(statement);
		}
		return statementsByStatuses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<LeaveStatement> getLeaveStatementsByStatus(EnumSet<StatementStatus> aStatuses) {
		Objects.requireNonNull(aStatuses);

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<LeaveStatementEntity> query = cb.createQuery(LeaveStatementEntity.class);
		Root<LeaveStatementEntity> root = query.from(LeaveStatementEntity.class);

		query.where(root.get(LeaveStatementEntity_.status).in(aStatuses));
		Collection<LeaveStatementEntity> result = _em.createQuery(query).getResultList();

		List<LeaveStatement> statementsByStatuses = new ArrayList<>();
		for (LeaveStatementEntity entity : result) {
			LeaveStatement statement = new EntityBasedLeaveStatementFactory(entity).create();
			statementsByStatuses.add(statement);
		}
		return statementsByStatuses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RecallStatement> getRecallStatementsByStatus(EnumSet<StatementStatus> aStatuses) {
		Objects.requireNonNull(aStatuses);

		CriteriaBuilder cb = _em.getCriteriaBuilder();
		CriteriaQuery<RecallStatementEntity> query = cb.createQuery(RecallStatementEntity.class);
		Root<RecallStatementEntity> root = query.from(RecallStatementEntity.class);

		query.where(root.get(RecallStatementEntity_.status).in(aStatuses));
		Collection<RecallStatementEntity> result = _em.createQuery(query).getResultList();

		List<RecallStatement> statementsByStatuses = new ArrayList<>();
		for (RecallStatementEntity entity : result) {
			RecallStatement statement = new EntityBasedRecallStatementFactory(entity).create();
			statementsByStatuses.add(statement);
		}
		return statementsByStatuses;
	}

}
