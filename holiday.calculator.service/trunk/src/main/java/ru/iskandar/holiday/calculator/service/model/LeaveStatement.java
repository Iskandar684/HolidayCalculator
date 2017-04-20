package ru.iskandar.holiday.calculator.service.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Заявление на отпуск
 */
public class LeaveStatement extends Statement {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 553333168079346415L;

	/** Дни отпуска */
	private final Set<Date> _leaveDates = new HashSet<>();

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор заявления
	 * @param aAuthor
	 *            автор заявления
	 */
	protected LeaveStatement(UUID aUUID, User aAuthor, Set<Date> aLeaveDates) {
		super(aUUID, aAuthor);
		Objects.requireNonNull(aLeaveDates);
		_leaveDates.addAll(aLeaveDates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatementType getStatementType() {
		return StatementType.LEAVE_STATEMENT;
	}

	public Set<Date> getLeaveDates() {
		return Collections.unmodifiableSet(_leaveDates);
	}

}
