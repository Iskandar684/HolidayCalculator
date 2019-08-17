/**
 *
 */
package ru.iskandar.holiday.calculator.ui.grid;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 *
 */
public class MultiTextCellEditor extends TextCellEditor {

	public MultiTextCellEditor(Composite parent) {
		super(parent, SWT.MULTI);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LayoutData getLayoutData() {
		LayoutData layoutData = super.getLayoutData();
		layoutData.minimumHeight = 300;
		return layoutData;
	}
}
