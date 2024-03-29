package ru.iskandar.holiday.calculator.service.entities;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 *
 */
public class DOBasedLeaveStatementEntityFactory extends LeaveStatementEntityFactory {

	private final LeaveStatement _statement;

	/**
	 * Конструктор
	 */
	public DOBasedLeaveStatementEntityFactory(LeaveStatement aHolidayStatement) {
		Objects.requireNonNull(aHolidayStatement);
		_statement = aHolidayStatement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<Date> getDays() {
		return _statement.getLeaveDates();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected StatementId getId() {
		return _statement.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected StatementStatus getStatus() {
		return _statement.getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UserId getAuthor() {
		User author = _statement.getAuthor();
		return author.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getCreateDate() {
		return _statement.getCreateDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UserId getConsider() {
		User consider = _statement.getConsider();
		if (consider == null) {
			return null;
		}
		return consider.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getConsiderDate() {
		return _statement.getConsiderDate();
	}

}
