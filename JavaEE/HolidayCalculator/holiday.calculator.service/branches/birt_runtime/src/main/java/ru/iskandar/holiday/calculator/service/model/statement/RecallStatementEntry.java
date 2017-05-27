package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Заявления на отзыв. Это заявление, где указаны дни, в которые сотрудник
 * работал во время отпуска. На основе этих дней зачислиются отгула.
 */
public class RecallStatementEntry extends StatementEntry {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -3698550979261360136L;

	/** Дни, в которые сотрудник работал во время отпуска */
	private final Set<Date> _recallDates = new HashSet<>();

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор заявления
	 * @param aAuthor
	 *            автор заявления
	 * @param aRecallDates
	 *            дни, в которые сотрудник работал во время отпуска
	 */
	public RecallStatementEntry(Set<Date> aRecallDates, User aAuthor) {
		super(aAuthor);
		Objects.requireNonNull(aRecallDates);
		_recallDates.addAll(aRecallDates);
	}

	/**
	 * @return the recallDates
	 */
	public Set<Date> getRecallDates() {
		return Collections.unmodifiableSet(_recallDates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatementType getStatementType() {
		return StatementType.RECALL_STATEMENT;
	}

}
