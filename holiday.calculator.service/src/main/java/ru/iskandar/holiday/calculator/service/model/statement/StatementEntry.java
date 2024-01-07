package ru.iskandar.holiday.calculator.service.model.statement;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Содержимое заявления
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public abstract class StatementEntry implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -5045249287091738362L;

	/** Автор заявления */
	@XmlElement(name = "author")
	private final User _author;

	/** Пользователь, который рассмотрел заявление */
	@XmlElement(name = "consider")
	private User _consider;

	/** Статус заявления */
	@XmlElement(name = "status")
	private StatementStatus _status = StatementStatus.NOT_CONSIDERED;

	/** Время подачи заявления */
	@XmlElement(name = "createDate")
	private Date _createDate = new Date();

	/** Время рассмотрения */
	@XmlElement(name = "considerDate")
	private Date _considerDate;

	/**
	 * Конструктор
	 *
	 * @param aStatementId
	 *            идентификатор заявления
	 */
	public StatementEntry(User aAuthor) {
		_author = Objects.requireNonNull(aAuthor, "Не указан автор заявления");
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

	public abstract StatementType getStatementType();

}
