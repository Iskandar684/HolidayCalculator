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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сущность заявления на отгул
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_holiday_statement")
@Getter
@Setter
@Accessors(prefix = "_")
@EqualsAndHashCode(of = "_uuid")
public class HolidayStatementEntity implements Serializable, IStatementEntity {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -8620736723647051296L;

	/** Идентификатор */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
	private UUID _uuid;

	/** Автор заявления */
	@Column(name = "author_uuid")
	@NotNull(message = "Не указан автор заявления")
	private UUID _author;

	/** Пользователь, который рассмотрел заявление */
	@Column(name = "consider_uuid")
	private UUID _consider;

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

	/** Дни отгула */
	@ElementCollection(targetClass = Date.class)
	@CollectionTable(name = "ru_iskandar_holiday_calculator_holiday_statement_days")
	private Set<Date> _days;

        @Override
        @Transient
        public UserId getAuthorId() {
            return _author == null ? null : UserId.fromString(_author);
        }

        @Override
        @Transient
        public UserId getConsiderId() {
            return _consider == null ? null : UserId.fromString(_consider);
        }

}
