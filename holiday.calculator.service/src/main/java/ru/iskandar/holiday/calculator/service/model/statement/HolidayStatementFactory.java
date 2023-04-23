package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Фабрика заявления на отгул
 */
public abstract class HolidayStatementFactory {

	/**
	 * Создает заявление на отгул
	 *
	 * @return заявление на отгул
	 */
	public HolidayStatement create() {
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

		HolidayStatementEntry entry = new HolidayStatementEntry(days, author);
		entry.setConsider(consider);
		entry.setConsiderDate(considerDate);
		entry.setStatus(status);
		entry.setCreateDate(createDate);

		HolidayStatement statement = new HolidayStatement(id, entry);
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
