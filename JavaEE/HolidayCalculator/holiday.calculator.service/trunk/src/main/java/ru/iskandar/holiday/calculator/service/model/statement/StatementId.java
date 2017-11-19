package ru.iskandar.holiday.calculator.service.model.statement;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Идентификатор заявления
 */
@XmlRootElement(name = "StatementId")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatementId implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -2897538723997072767L;

	/** UUID заявления */
	@XmlElement(name = "uuid")
	private final UUID _uuid;

	/**
	 * Конструктор
	 */
	private StatementId(UUID aUUID) {
		Objects.requireNonNull(aUUID);
		_uuid = aUUID;
	}

	public static StatementId from(UUID aUUID) {
		Objects.requireNonNull(aUUID);
		return new StatementId(aUUID);
	}

	/**
	 * @return the uuid
	 */
	public UUID getUUID() {
		return _uuid;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StatementId other = (StatementId) obj;
		if (_uuid == null) {
			if (other._uuid != null) {
				return false;
			}
		} else if (!_uuid.equals(other._uuid)) {
			return false;
		}
		return true;
	}

}
