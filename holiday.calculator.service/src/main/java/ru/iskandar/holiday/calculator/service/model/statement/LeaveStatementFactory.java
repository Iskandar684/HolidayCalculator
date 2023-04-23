package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Фабрика заявления на отпуск
 */
public abstract class LeaveStatementFactory {

	/**
	 * Создает заявление на отпуск
	 *
	 * @return заявление на отпуск
	 */
	public LeaveStatement create() {
		Set<Date> days = getDays();
		StatementId id = getId();
		StatementStatus status = getStatus();
		User author = getAuthor();
		Date createDate = getCreateDate();
		User consider = getConsider();
		Date considerDate = getConsiderDate();

		Objects.requireNonNull(days);
		Objects.requireNonNull(id);
		Objects.requireNonNull(status);
		Objects.requireNonNull(author);
		Objects.requireNonNull(createDate);

		if (StatementStatus.APPROVE.equals(status) || StatementStatus.REJECTED.equals(status)) {
			Objects.requireNonNull(consider);
			Objects.requireNonNull(considerDate);
		}

		LeaveStatementEntry entry = new LeaveStatementEntry(author, days);
		entry.setConsider(consider);
		entry.setConsiderDate(considerDate);
		entry.setStatus(status);
		entry.setCreateDate(createDate);

		LeaveStatement statement = new LeaveStatement(id, entry);
		return statement;
	}

	/**
	 * @return the days
	 */
	protected abstract Set<Date> getDays();

	/**
	 * @return the uuid
	 */
	protected abstract StatementId getId();

	/**
	 * @return the status
	 */
	protected abstract StatementStatus getStatus();

	/**
	 * @return the author
	 */
	protected abstract User getAuthor();

	/**
	 * @return the createDate
	 */
	protected abstract Date getCreateDate();

	/**
	 * @return the consider
	 */
	protected abstract User getConsider();

	/**
	 * @return the considerDate
	 */
	protected abstract Date getConsiderDate();

}
