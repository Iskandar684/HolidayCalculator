package ru.iskandar.holiday.calculator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

public class NavigationViewPart extends ViewPart {

	public static final String ID = "ru.iskandar.holiday.calculator.ui.NavigationViewPart";

	public NavigationViewPart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite aParent) {
		final FormToolkit toolkit = new FormToolkit(Display.getDefault());
		final Composite main = toolkit.createComposite(aParent);
		main.setLayout(new GridLayout());
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label fio = toolkit.createLabel(aParent, "ФИО");
		fio.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
