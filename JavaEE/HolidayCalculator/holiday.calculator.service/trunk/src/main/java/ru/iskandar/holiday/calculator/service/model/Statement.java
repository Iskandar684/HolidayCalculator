package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
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

	/** Статус заявления */
	private StatementStatus _status = StatementStatus.NOT_CONSIDERED;

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор заявления
	 */
	public Statement(UUID aUUID) {
		_uuid = aUUID;
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

}
