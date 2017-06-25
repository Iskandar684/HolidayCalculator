package ru.iskandar.holiday.calculator.service.ejb.report;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Контейнер-параметр для формирования документа "Заявления на отпуск"
 */
public class HolidayStatementReportParameter implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -2496695956098513022L;

	/** Автор */
	private User _author;

	private Set<Date> _dates;

	/**
	 *
	 */
	HolidayStatementReportParameter(User aAuthor, Set<Date> aDates) {
		Objects.requireNonNull(aAuthor);
		Objects.requireNonNull(aDates);
		_author = aAuthor;
		_dates = aDates;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return _author;
	}

	/**
	 * @return the dates
	 */
	public Set<Date> getDates() {
		return Collections.unmodifiableSet(_dates);
	}

	public String format(Set<Date> aDates) {
		return DateUtils.toString(aDates);
	}

}
