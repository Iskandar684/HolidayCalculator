package ru.iskandar.holiday.calculator.service.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Заявления на отзыв. Это заявление, где указаны дни, в которые сотрудник
 * работал во время отпуска. На основе этих дней зачислиются отгула.
 */
public class RecallStatement extends Statement {

	/** Дни, в которые сотрудник работал во время отпуска */
	private final Set<Date> _recallDates = new HashSet<>();

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -6055526811135206004L;

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
	public RecallStatement(UUID aUUID, User aAuthor, Set<Date> aRecallDates) {
		super(aUUID, aAuthor);
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
