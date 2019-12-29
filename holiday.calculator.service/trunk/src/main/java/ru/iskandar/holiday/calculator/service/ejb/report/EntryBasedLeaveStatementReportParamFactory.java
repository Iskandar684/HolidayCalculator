package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public class EntryBasedLeaveStatementReportParamFactory {

	private final LeaveStatementEntry _entry;

	/**
	 *
	 */
	public EntryBasedLeaveStatementReportParamFactory(LeaveStatementEntry aEntry) {
		Objects.requireNonNull(aEntry);
		_entry = aEntry;
	}

	public LeaveStatementReportParameter create() {
		User author = getAuthor();
		if (author == null) {
			throw new IllegalStateException("Не указан автор");
		}
		Set<Date> dates = getDates();
		LeaveStatementReportParameter param = new LeaveStatementReportParameter(author, dates);
		return param;
	}

	protected User getAuthor() {
		return _entry.getAuthor();
	}

	protected Set<Date> getDates() {
		return _entry.getLeaveDates();
	}

}
