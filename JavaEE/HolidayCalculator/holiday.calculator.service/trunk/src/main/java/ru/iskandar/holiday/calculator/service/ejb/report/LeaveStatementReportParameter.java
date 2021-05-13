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
public class LeaveStatementReportParameter implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 7994456709833475037L;

	/** Автор */
	private final User _author;

	private final Set<Date> _dates;

	/**
	 *
	 */
	LeaveStatementReportParameter(User aAuthor, Set<Date> aDates) {
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