package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Заявление
 */
public abstract class Statement implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 3589182703953347929L;

	/** Идентификатор заявления */
	private final UUID _uuid;

	/** Автор заявления */
	private final User _author;

	/** Пользователь, который рассмотрел заявление */
	private User _consider;

	/** Статус заявления */
	private StatementStatus _status = StatementStatus.NOT_CONSIDERED;

	/** Время подачи заявления */
	private Date _createDate = new Date();

	/** Время рассмотрения */
	private Date _considerDate;

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор заявления
	 */
	public Statement(UUID aUUID, User aAuthor) {
		Objects.requireNonNull(aUUID, "Не указан идентификатор заявления");
		Objects.requireNonNull(aAuthor, "Не указан автор заявления");
		_uuid = aUUID;
		_author = aAuthor;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return _uuid;
	}

	/**
	 * @return the status
	 */
	public StatementStatus getStatus() {
		return _status;
	}

	/**
	 * @param aStatus
	 *            the status to set
	 */
	public void setStatus(StatementStatus aStatus) {
		Objects.requireNonNull(aStatus);
		_status = aStatus;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return _author;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * @param aCreateDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date aCreateDate) {
		_createDate = aCreateDate;
	}

	/**
	 * @return the consider
	 */
	public User getConsider() {
		return _consider;
	}

	/**
	 * @param aConsider
	 *            the consider to set
	 */
	public void setConsider(User aConsider) {
		_consider = aConsider;
	}

	/**
	 * @return the considerDate
	 */
	public Date getConsiderDate() {
		return _considerDate;
	}

	/**
	 * @param aConsiderDate
	 *            the considerDate to set
	 */
	public void setConsiderDate(Date aConsiderDate) {
		_considerDate = aConsiderDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((_uuid == null) ? 0 : _uuid.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statement other = (Statement) obj;
		if (_uuid == null) {
			if (other._uuid != null)
				return false;
		} else if (!_uuid.equals(other._uuid))
			return false;
		return true;
	}

	public abstract StatementType getStatementType();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass());
		builder.append(" uuid=");
		builder.append(_uuid);
		return builder.toString();
	}

}