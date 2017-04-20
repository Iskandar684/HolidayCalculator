package ru.iskandar.holiday.calculator.service.entities;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.StatementId;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public class DOBasedHolidayStatementEntityFactory extends HolidayStatementEntityFactory {

	private final HolidayStatement _statement;

	/**
	 * Конструктор
	 */
	public DOBasedHolidayStatementEntityFactory(HolidayStatement aHolidayStatement) {
		Objects.requireNonNull(aHolidayStatement);
		_statement = aHolidayStatement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<Date> getDays() {
		return _statement.getDays();
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
	protected UserEntity getAuthor() {
		User author = _statement.getAuthor();
		return new DOBasedUserEntityFactory(author).create();
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
	protected UserEntity getConsider() {
		User consider = _statement.getConsider();
		if (consider == null) {
			return null;
		}
		return new DOBasedUserEntityFactory(consider).create();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Date getConsiderDate() {
		return _statement.getConsiderDate();
	}

}
