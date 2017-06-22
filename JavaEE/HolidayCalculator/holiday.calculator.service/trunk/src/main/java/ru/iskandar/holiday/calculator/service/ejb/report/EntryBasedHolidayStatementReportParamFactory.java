/**
 *
 */
package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * @author Искандар
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

}
