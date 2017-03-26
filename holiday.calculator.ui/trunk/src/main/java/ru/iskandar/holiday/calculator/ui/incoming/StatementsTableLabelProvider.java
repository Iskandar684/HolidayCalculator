package ru.iskandar.holiday.calculator.ui.incoming;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementType;
import ru.iskandar.holiday.calculator.service.model.User;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.incoming.StatementsTableCreator.StatementsTableColumn;

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

			default:
				break;
			}
			break;

		case AUTHOR:
			User author = statement.getAuthor();
			text = String.format("%s %s %s", author.getLastName(), author.getFirstName(), author.getPatronymic());
			break;

		case CREATE_DATE:
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			text = dateFormatter.format(statement.getCreateDate());
			break;

		case REVIEW_DATE:
			// TODO
			text = "-";
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

		default:
			text = statement.toString();
			break;
		}
		return text;
	}

}
