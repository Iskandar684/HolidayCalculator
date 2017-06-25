/**
 *
 */
package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public class EntryBasedHolidayStatementReportParamFactory extends HolidayStatementReportParameterFactory {

	private HolidayStatementEntry _entry;

	/**
	 *
	 */
	public EntryBasedHolidayStatementReportParamFactory(HolidayStatementEntry aEntry) {
		Objects.requireNonNull(aEntry);
		_entry = aEntry;
	}

	@Override
	protected User getAuthor() {
		return _entry.getAuthor();
	}

	@Override
	protected Set<Date> getDates() {
		return _entry.getDays();
	}

}
