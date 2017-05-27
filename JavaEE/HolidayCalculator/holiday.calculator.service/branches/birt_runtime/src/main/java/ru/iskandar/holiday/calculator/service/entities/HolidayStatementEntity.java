package ru.iskandar.holiday.calculator.service.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity;

/**
 * Сущность заявления на отгул
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_holiday_statement")
public class HolidayStatementEntity implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -8620736723647051296L;

	/** Идентификатор */
	private UUID _uuid;

	/** Автор заявления */
	private UserEntity _author;

	/** Пользователь, который рассмотрел заявление */
	private UserEntity _consider;

	/** Статус заявления */
	private StatementStatus _status;

	/** Время подачи заявления */
	private Date _createDate;

	/** Время рассмотрения */
	private Date _considerDate;

	/** Дни отгула */
	private Set<Date> _days;

	/**
	 * @return the uuid
	 */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
	public UUID getUuid() {
		return _uuid;
	}

	/**
	 * @param aUuid
	 *            the uuid to set
	 */
	public void setUuid(UUID aUuid) {
		_uuid = aUuid;
	}

	/**
	 * @return the author
	 */
	@ManyToOne()
	@JoinColumn(name = "author")
	@NotNull(message = "Не указан автор заявления")
	public UserEntity getAuthor() {
		return _author;
	}

	/**
	 * @param aAuthor
	 *            the author to set
	 */
	public void setAuthor(UserEntity aAuthor) {
		_author = aAuthor;
	}

	/**
	 * @return the consider
	 */
	@ManyToOne()
	@JoinColumn(name = "consider")
	public UserEntity getConsider() {
		return _consider;
	}

	/**
	 * @param aConsider
	 *            the consider to set
	 */
	public void setConsider(UserEntity aConsider) {
		_consider = aConsider;
	}

	/**
	 * @return the status
	 */
	@Column(name = "status")
	@NotNull(message = "Не указан статус заявления")
	@Enumerated(EnumType.STRING)
	public StatementStatus getStatus() {
		return _status;
	}

	/**
	 * @param aStatus
	 *            the status to set
	 */
	public void setStatus(StatementStatus aStatus) {
		_status = aStatus;
	}

	/**
	 * @return the createDate
	 */
	@Column(name = "create_date")
	@NotNull(message = "Не указано время подачи заявления")
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
	 * @return the considerDate
	 */
	@Column(name = "consider_date")
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
	 * @return the days
	 */
	@ElementCollection(targetClass = Date.class)
	@CollectionTable(name = "ru_iskandar_holiday_calculator_holiday_statement_days")
	public Set<Date> getDays() {
		return _days;
	}

	/**
	 * @param aDays
	 *            the days to set
	 */
	public void setDays(Set<Date> aDays) {
		_days = aDays;
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
		HolidayStatementEntity other = (HolidayStatementEntity) obj;
		if (_uuid == null) {
			if (other._uuid != null)
				return false;
		} else if (!_uuid.equals(other._uuid))
			return false;
		return true;
	}

}
