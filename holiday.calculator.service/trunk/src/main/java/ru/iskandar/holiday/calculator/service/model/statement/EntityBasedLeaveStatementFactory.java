package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.entities.LeaveStatementEntity;
import ru.iskandar.holiday.calculator.service.entities.UserEntity;
import ru.iskandar.holiday.calculator.service.model.user.EntityBasedUserFactory;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public class EntityBasedLeaveStatementFactory extends LeaveStatementFactory {

	private final LeaveStatementEntity _entity;

	/**
	 * Конструктор
	 */
	public EntityBasedLeaveStatementFactory(LeaveStatementEntity aEntity) {
		Objects.requireNonNull(aEntity);
		_entity = aEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<Date> getDays() {
		return _entity.getDays();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected StatementId getId() {
		return StatementId.from(_entity.getUuid());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected StatementStatus getStatus() {
		return _entity.getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected User getAuthor() {
		UserEntity authorEntity = _entity.getAuthor();
		return new EntityBasedUserFactory(authorEntity).create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getCreateDate() {
		return _entity.getCreateDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected User getConsider() {
		UserEntity considerEntity = _entity.getConsider();
		if (considerEntity == null) {
			return null;
		}
		return new EntityBasedUserFactory(considerEntity).create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getConsiderDate() {
		return _entity.getConsiderDate();
	}

}
