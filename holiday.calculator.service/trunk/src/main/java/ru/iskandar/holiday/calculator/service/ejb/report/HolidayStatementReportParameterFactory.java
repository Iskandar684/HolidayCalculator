package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.Date;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public abstract class HolidayStatementReportParameterFactory {

	public HolidayStatementReportParameter create() {
		User author = getAuthor();
		if (author == null) {
			throw new IllegalStateException("Не указан автор");
		}
		Set<Date> dates = getDates();
		HolidayStatementReportParameter param = new HolidayStatementReportParameter(author, dates);
		return param;
	}

	protected abstract User getAuthor();

	protected abstract Set<Date> getDates();

}
