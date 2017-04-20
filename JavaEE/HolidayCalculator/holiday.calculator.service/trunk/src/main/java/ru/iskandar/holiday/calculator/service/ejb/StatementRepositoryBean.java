package ru.iskandar.holiday.calculator.service.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import ru.iskandar.holiday.calculator.service.entities.HolidayStatementEntity;
import ru.iskandar.holiday.calculator.service.entities.HolidayStatementEntity_;
import ru.iskandar.holiday.calculator.service.entities.UserEntity;
import ru.iskandar.holiday.calculator.service.model.EntityBasedHolidayStatementFactory;
import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementId;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementType;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Бин репозитория заявлений
 */
@Stateless
@Local(IStatementRepository.class)
public class StatementRepositoryBean implements IStatementRepository {

	/** Менеджер сущностей */
	@PersistenceContext
	private EntityManager _em;

	// TODO имитация БД
	private static Map<StatementId, Statement> _statements = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Statement> getStatementsByAuthor(User aAuthor) {
		Objects.requireNonNull(aAuthor);
		Set<Statement> statementsByUser = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(aAuthor)) {
				statementsByUser.add(st);
			}
		}
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
		query.where(cb.equal(root.get(HolidayStatementEntity_._author), userEntity));
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
		Set<RecallStatement> currentUserStatements = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(aAuthor) && (st instanceof RecallStatement)) {
				currentUserStatements.add((RecallStatement) st);
			}
		}
		return currentUserStatements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<LeaveStatement> getLeaveStatementsByAuthor(User aAuthor) {
		Set<LeaveStatement> currentUserStatements = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(aAuthor) && (st instanceof LeaveStatement)) {
				currentUserStatements.add((LeaveStatement) st);
			}
		}
		return currentUserStatements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Statement> getStatementsByStatus(EnumSet<StatementStatus> aStatuses) {
		Set<Statement> res = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (aStatuses.contains(st.getStatus())) {
				res.add(st);
			}
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HolidayStatement getHolidayStatement(StatementId aStatementUUID) {
		Objects.requireNonNull(aStatementUUID);

		HolidayStatementEntity entity = _em.find(HolidayStatementEntity.class, aStatementUUID.getUuid());
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
		return (LeaveStatement) _statements.get(aStatementUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecallStatement getRecallStatement(StatementId aStatementUUID) {

		return (RecallStatement) _statements.get(aStatementUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement getStatement(StatementId aUUID, StatementType aType) {
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
			throw new IllegalArgumentException(String.format("Тип заявления не поддерживается", aType));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Statement aStatement) {
		Objects.requireNonNull(aStatement);
		switch (aStatement.getStatementType()) {
		case HOLIDAY_STATEMENT:
			HolidayStatementEntity entity = new DOBasedHolidayStatementEntityFactory((HolidayStatement) aStatement)
					.create();
			if (_em.find(HolidayStatementEntity.class, aStatement.getId().getUuid()) == null) {
				_em.persist(entity);
			} else {
				_em.merge(entity);
			}
			break;

		default:
			break;
		}

		_statements.put(aStatement.getId(), aStatement);
	}

}
