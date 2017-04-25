package ru.iskandar.holiday.calculator.service.entities;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.StatementId;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;

/**
 * Фабрика создания сущности заявления на отзыв
 */
public abstract class RecallStatementEntityFactory {

	/**
	 * Создает заявление на отпуск
	 *
	 * @return заявление на отпуск
	 */
	public RecallStatementEntity create() {
		Set<Date> days = getDays();
		StatementId id = getId();
		StatementStatus status = getStatus();
		UserEntity author = getAuthor();
		Date createDate = getCreateDate();
		UserEntity consider = getConsider();
		Date considerDate = getConsiderDate();

		Objects.requireNonNull(days);
		Objects.requireNonNull(status);
		Objects.requireNonNull(author);
		Objects.requireNonNull(createDate);

		if (StatementStatus.APPROVE.equals(status) || StatementStatus.REJECTED.equals(status)) {
			Objects.requireNonNull(consider);
			Objects.requireNonNull(considerDate);
		}

		RecallStatementEntity statement = new RecallStatementEntity();
		// У несохраненных в БД сущностей первичный ключ должен быть null
		statement.setUuid(id != null ? id.getUUID() : null);
		statement.setDays(days);
		statement.setAuthor(author);
		statement.setConsider(consider);
		statement.setConsiderDate(considerDate);
		statement.setStatus(status);
		statement.setCreateDate(createDate);
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
	protected abstract UserEntity getAuthor();

	/**
	 * @return the createDate
	 */
	protected abstract Date getCreateDate();

	/**
	 * @return the consider
	 */
	protected abstract UserEntity getConsider();

	/**
	 * @return the considerDate
	 */
	protected abstract Date getConsiderDate();

}
