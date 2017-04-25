package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Заявление на отпуск
 */
public class LeaveStatementEntry extends StatementEntry {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -4072880206521207146L;

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
	public LeaveStatementEntry(User aAuthor, Set<Date> aLeaveDates) {
		super(aAuthor);
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
