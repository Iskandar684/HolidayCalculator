package ru.iskandar.holiday.calculator.service.ejb.report;

import java.io.Serializable;
import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.user.User;

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

	/**
	 *
	 */
	HolidayStatementReportParameter(User aAuthor) {
		Objects.requireNonNull(aAuthor);
		_author = aAuthor;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return _author;
	}

}
