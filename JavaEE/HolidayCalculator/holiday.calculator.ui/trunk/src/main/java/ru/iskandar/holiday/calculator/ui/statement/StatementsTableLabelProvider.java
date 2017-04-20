package ru.iskandar.holiday.calculator.ui.statement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementType;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.statement.StatementsTableCreator.StatementsTableColumn;

/**
 * Поставщик текста таблицы заявлений
 */
public class StatementsTableLabelProvider implements ITableLabelProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(ILabelProviderListener aListener) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLabelProperty(Object aElement, String aProperty) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(ILabelProviderListener aListener) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getColumnImage(Object aElement, int aColumnIndex) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnText(Object aElement, int aColumnIndex) {
		StatementsTableColumn col = StatementsTableColumn.findColumnByIndex(aColumnIndex);
		Statement statement = (Statement) aElement;
		String text = "";
		switch (col) {
		case TYPE:
			StatementType stType = statement.getStatementType();
			switch (stType) {
			case HOLIDAY_STATEMENT:
				text = Messages.holidayStatement;
				break;

			case RECALL_STATEMENT:
				text = Messages.recallStatement;
				break;

			case LEAVE_STATEMENT:
				text = Messages.leaveStatement;
				break;

			default:
				break;
			}
			break;

		case AUTHOR:
			User author = statement.getAuthor();
			text = toString(author);
			break;

		case CREATE_DATE:
			text = toString(statement.getCreateDate());
			break;

		case STATUS:
			StatementStatus status = statement.getStatus();
			switch (status) {
			case APPROVE:
				text = Messages.approve;
				break;

			case NOT_CONSIDERED:
				text = Messages.notConsidered;
				break;

			case REJECTED:
				text = Messages.rejected;
				break;
			default:
				text = status.name();
				break;
			}
			break;

		case CONSIDER:
			text = toString(statement.getConsider());
			break;

		case CONSIDER_DATE:
			text = toString(statement.getConsiderDate());
			break;

		case CONTENT:
			text = getContent(statement);
			break;

		default:
			text = statement.toString();
			break;
		}
		return text;
	}

	private String getContent(Statement aStatement) {
		switch (aStatement.getStatementType()) {
		case HOLIDAY_STATEMENT:
			return getContent((HolidayStatement) aStatement);

		case LEAVE_STATEMENT:
			return getContent((LeaveStatement) aStatement);

		case RECALL_STATEMENT:
			return getContent((RecallStatement) aStatement);
		default:
			break;
		}
		return aStatement.toString();
	}

	private String getContent(HolidayStatement aStatement) {
		StringBuilder builder = new StringBuilder();
		List<Date> days = new ArrayList<>(aStatement.getDays());
		Collections.sort(days);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

		Iterator<Date> it = days.iterator();

		while (it.hasNext()) {
			Date date = it.next();
			builder.append(dateFormatter.format(date));
			if (it.hasNext())
				builder.append("; ");
		}

		return builder.toString();
	}

	private String getContent(LeaveStatement aStatement) {
		StringBuilder builder = new StringBuilder();
		List<Date> days = new ArrayList<>(aStatement.getLeaveDates());
		Collections.sort(days);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

		Iterator<Date> it = days.iterator();

		while (it.hasNext()) {
			Date date = it.next();
			builder.append(dateFormatter.format(date));
			if (it.hasNext())
				builder.append("; ");
		}

		return builder.toString();
	}

	private String getContent(RecallStatement aStatement) {
		StringBuilder builder = new StringBuilder();
		List<Date> days = new ArrayList<>(aStatement.getRecallDates());
		Collections.sort(days);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

		Iterator<Date> it = days.iterator();

		while (it.hasNext()) {
			Date date = it.next();
			builder.append(dateFormatter.format(date));
			if (it.hasNext())
				builder.append("; ");
		}

		return builder.toString();
	}

	private String toString(User aUser) {
		if (aUser == null) {
			return "-";
		}
		String text = String.format("%s %s %s", aUser.getLastName(), aUser.getFirstName(), aUser.getPatronymic());
		return text;
	}

	private String toString(Date aDate) {
		if (aDate == null) {
			return "-";
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String text = dateFormatter.format(aDate);
		return text;
	}

}
