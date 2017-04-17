package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementType;
import ru.iskandar.holiday.calculator.service.model.User;

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
	private static Map<UUID, Statement> _statements = new HashMap<>();

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
		Set<HolidayStatement> currentUserStatements = new HashSet<>();
		for (Statement st : _statements.values()) {
			if (st.getAuthor().equals(aAuthor) && (st instanceof HolidayStatement)) {
				currentUserStatements.add((HolidayStatement) st);
			}
		}
		return currentUserStatements;
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
	public HolidayStatement getHolidayStatement(UUID aStatementUUID) {
		return (HolidayStatement) _statements.get(aStatementUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveStatement getLeaveStatement(UUID aStatementUUID) {
		return (LeaveStatement) _statements.get(aStatementUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RecallStatement getRecallStatement(UUID aStatementUUID) {

		return (RecallStatement) _statements.get(aStatementUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement getStatement(UUID aUUID, StatementType aType) {
		return _statements.get(aUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Statement aStatement) {
		_statements.put(aStatement.getUuid(), aStatement);
	}

}
