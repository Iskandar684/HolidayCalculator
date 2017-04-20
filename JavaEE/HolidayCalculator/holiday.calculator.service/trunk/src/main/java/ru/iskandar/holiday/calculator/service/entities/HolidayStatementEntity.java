package ru.iskandar.holiday.calculator.service.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ru.iskandar.holiday.calculator.service.model.StatementStatus;

/**
 * Сущность заявления на отгул
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_holiday_statement")
public class HolidayStatementEntity {

	/** Идентификатор */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
	private UUID _uuid;

	/** Автор заявления */
	@ManyToOne
	@JoinColumn(name = "author")
	@NotNull(message = "Не указан автор заявления")
	private UserEntity _author;

	/** Пользователь, который рассмотрел заявление */
	@ManyToOne
	@JoinColumn(name = "consider")
	private UserEntity _consider;

	/** Статус заявления */
	@Column(name = "status")
	@NotNull(message = "Не указан статус заявления")
	private StatementStatus _status;

	/** Время подачи заявления */
	@Column(name = "create_date")
	@NotNull(message = "Не указано время подачи заявления")
	private Date _createDate;

	/** Время рассмотрения */
	@Column(name = "consider_date")
	private Date _considerDate;

	/** Дни отгула */
	// @JoinTable(name =
	// "ru_iskandar_holiday_calculator_holiday_statement_holiday_dates",
	// joinColumns = @JoinColumn(name = "statement_id"), inverseJoinColumns =
	// @JoinColumn(name = "dates_id"))
	@ElementCollection(targetClass = Date.class)
	private Set<Date> _days;

	/**
	 * @return the uuid
	 */
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

}
