package ru.iskandar.holiday.calculator.ui.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import ru.iskandar.holiday.calculator.ui.user.UsersTableCreator.UsersTableColumn;
import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Поставщик текста таблицы заявлений
 */
public class UsersTableLabelProvider implements ITableLabelProvider {

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
		UsersTableColumn col = UsersTableColumn.findColumnByIndex(aColumnIndex);
		User user = (User) aElement;
		String text = UsersTableProperties.EMPTY;
		switch (col) {
		case FIRST_NAME:
			text = user.getFirstName();
			break;

		case LAST_NAME:
			text = user.getLastName();
			break;

		case PATRONYMIC:
			text = user.getPatronymic();
			break;

		case LOGIN:
			text = user.getLogin();
			break;

		case EMPLOYMENT:
			text = toString(user.getEmploymentDate());
			break;

		case NOTE:
			text = user.getNote();
			break;

		default:
			text = user.toString();
			break;
		}
		if (text == null) {
			text = UsersTableProperties.EMPTY;
		}
		return text;
	}

	private String toString(Date aDate) {
		if (aDate == null) {
			return "-";
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
		String text = dateFormatter.format(aDate);
		return text;
	}

}
