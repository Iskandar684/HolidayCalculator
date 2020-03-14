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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.user.UserEntity;

/**
 * Сущность заявления на отпуск
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_recall_statement")
@Getter
@Setter
@Accessors(prefix = "_")
@EqualsAndHashCode(of = "_uuid")
public class RecallStatementEntity implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 6015143660823145464L;

	/** Идентификатор */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
	private UUID _uuid;

	/** Автор заявления */
	@ManyToOne()
	@JoinColumn(name = "author")
	@NotNull(message = "Не указан автор заявления")
	private UserEntity _author;

	/** Пользователь, который рассмотрел заявление */
	@ManyToOne()
	@JoinColumn(name = "consider")
	private UserEntity _consider;

	/** Статус заявления */
	@Column(name = "status")
	@NotNull(message = "Не указан статус заявления")
	@Enumerated(EnumType.STRING)
	private StatementStatus _status;

	/** Время подачи заявления */
	@Column(name = "create_date")
	@NotNull(message = "Не указано время подачи заявления")
	private Date _createDate;

	/** Время рассмотрения */
	@Column(name = "consider_date")
	private Date _considerDate;

	/** Дни отзыва */
	@ElementCollection(targetClass = Date.class)
	@CollectionTable(name = "ru_iskandar_holiday_calculator_recall_statement_days")
	private Set<Date> _days;

}
