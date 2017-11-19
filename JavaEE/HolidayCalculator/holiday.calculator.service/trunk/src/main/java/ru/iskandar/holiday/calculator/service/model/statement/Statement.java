package ru.iskandar.holiday.calculator.service.model.statement;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Заявление
 */
@XmlRootElement(name = "Statement")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Statement<E extends StatementEntry> implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 3589182703953347929L;

	/** Идентификатор заявления */
	@XmlElement(name = "statementId")
	private final StatementId _statementId;

	/** Содержимое заявления */
	@XmlElement(name = "entry")
	private final E _entry;

	/**
	 * Конструктор
	 *
	 * @param aStatementId
	 *            идентификатор заявления
	 */
	public Statement(StatementId aStatementId, E aStatementEntry) {
		Objects.requireNonNull(aStatementId, "Не указан идентификатор заявления");
		Objects.requireNonNull(aStatementEntry, "Не указано содержимое заявления");
		_entry = aStatementEntry;
		_statementId = aStatementId;
	}

	/**
	 * @return the status
	 */
	public StatementStatus getStatus() {
		return _entry.getStatus();
	}

	/**
	 * @param aStatus
	 *            the status to set
	 */
	public void setStatus(StatementStatus aStatus) {
		Objects.requireNonNull(aStatus);
		_entry.setStatus(aStatus);
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return _entry.getAuthor();
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return _entry.getCreateDate();
	}

	/**
	 * @param aCreateDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date aCreateDate) {
		_entry.setCreateDate(aCreateDate);
	}

	/**
	 * @return the consider
	 */
	public User getConsider() {
		return _entry.getConsider();
	}

	/**
	 * @param aConsider
	 *            the consider to set
	 */
	public void setConsider(User aConsider) {
		_entry.setConsider(aConsider);
	}

	/**
	 * @return the considerDate
	 */
	public Date getConsiderDate() {
		return _entry.getConsiderDate();
	}

	/**
	 * @param aConsiderDate
	 *            the considerDate to set
	 */
	public void setConsiderDate(Date aConsiderDate) {
		_entry.setConsiderDate(aConsiderDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((_statementId == null) ? 0 : _statementId.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Statement<?> other = (Statement<?>) obj;
		if (_statementId == null) {
			if (other._statementId != null) {
				return false;
			}
		} else if (!_statementId.equals(other._statementId)) {
			return false;
		}
		return true;
	}

	public final StatementType getStatementType() {
		return _entry.getStatementType();
	}

	/**
	 * Возвращает идентификатор заявления
	 *
	 * @return идентификатор заявления
	 */
	public StatementId getId() {
		return _statementId;
	}

	/**
	 * @return the entry
	 */
	protected E getEntry() {
		return _entry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass());
		builder.append(" id=");
		builder.append(_statementId);
		return builder.toString();
	}

}
