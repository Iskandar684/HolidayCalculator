package ru.iskandar.holiday.calculator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import ru.iskandar.holiday.calculator.service.model.User;
import ru.iskandar.holiday.calculator.ui.UserAttributesForm.IUserProvider;

/**
 * Панель навигации
 */
public class NavigationViewPart extends ViewPart {

	public static final String ID = "ru.iskandar.holiday.calculator.ui.NavigationViewPart";

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider = new HolidayCalculatorModelProvider();

	/**
	 * Конструктор
	 */
	public NavigationViewPart() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite aParent) {
		final FormToolkit toolkit = new FormToolkit(Display.getDefault());
		final Composite main = toolkit.createComposite(aParent, SWT.BORDER);
		main.setLayout(new GridLayout());
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		UserAttributesForm form = new UserAttributesForm(main, toolkit, new FormUserProvider());
		form.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
	}

	/**
	 * Поставщик пользователя для формы
	 *
	 */
	private class FormUserProvider implements IUserProvider {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public User getUser() {
			User user = _modelProvider.getModel().getCurrentUser();
			return user;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
