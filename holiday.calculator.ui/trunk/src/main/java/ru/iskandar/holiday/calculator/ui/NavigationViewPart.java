package ru.iskandar.holiday.calculator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import ru.iskandar.holiday.calculator.service.model.User;

public class NavigationViewPart extends ViewPart {

	public static final String ID = "ru.iskandar.holiday.calculator.ui.NavigationViewPart";

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider = new HolidayCalculatorModelProvider();

	public NavigationViewPart() {
	}

	@Override
	public void createPartControl(Composite aParent) {
		final FormToolkit toolkit = new FormToolkit(Display.getDefault());
		final Composite main = toolkit.createComposite(aParent);
		main.setLayout(new GridLayout());
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		User user = _modelProvider.getModel().getCurrentUser();
		final Label fio = toolkit.createLabel(aParent,
				String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getPatronymic()));
		fio.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
