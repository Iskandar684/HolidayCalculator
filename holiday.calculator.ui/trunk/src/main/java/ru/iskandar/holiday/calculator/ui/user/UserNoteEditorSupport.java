package ru.iskandar.holiday.calculator.ui.user;

import java.util.Objects;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

/**
 *
 */
public class UserNoteEditorSupport extends EditingSupport {

	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public UserNoteEditorSupport(ColumnViewer aViewer, HolidayCalculatorModelProvider aModelProvider) {
		super(aViewer);
		_modelProvider = Objects.requireNonNull(aModelProvider);
	}

	@Override
	protected CellEditor getCellEditor(Object aElement) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected boolean canEdit(Object aElement) {
		// XXX добавить проверку
		return true;
	}

	@Override
	protected Object getValue(Object aElement) {
		return ((User) aElement).getNote();
	}

	@Override
	protected void setValue(Object aElement, Object aValue) {
		_modelProvider.getModel().changeUserNote((User) aElement, (String) aValue);
	}

}
