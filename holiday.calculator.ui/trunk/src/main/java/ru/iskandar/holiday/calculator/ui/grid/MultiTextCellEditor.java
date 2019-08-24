package ru.iskandar.holiday.calculator.ui.grid;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Редактор ячейки таблицы для многострочного текста.
 */
public class MultiTextCellEditor extends TextCellEditor {

	public MultiTextCellEditor(Composite parent) {
		super(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.H_SCROLL);
	}

	@Override
	public LayoutData getLayoutData() {
		LayoutData layoutData = new LayoutData();
		layoutData.minimumHeight = 100;
		return layoutData;
	}
}
