package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public class EntryBasedRecallStatementReportParamFactory {

	private final RecallStatementEntry _entry;

	/**
	 *
	 */
	public EntryBasedRecallStatementReportParamFactory(RecallStatementEntry aEntry) {
		Objects.requireNonNull(aEntry);
		_entry = aEntry;
	}

	public RecallStatementReportParameter create() {
		User author = getAuthor();
		if (author == null) {
			throw new IllegalStateException("Не указан автор");
		}
		Set<Date> dates = getDates();
		RecallStatementReportParameter param = new RecallStatementReportParameter(author, dates);
		return param;
	}

	protected User getAuthor() {
		return _entry.getAuthor();
	}

	protected Set<Date> getDates() {
		return _entry.getRecallDates();
	}

}
