package ru.iskandar.holiday.calculator.ui;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

import ru.iskandar.holiday.calculator.service.model.User;

/**
 * Форма отображения атрибутов пользователя
 */
public class UserAttributesForm extends Composite {

	/** Поставщик пользователя */
	private final IUserProvider _userProvider;
	/** Инструментарий для создания пользовательского интерфейса */
	private final FormToolkit _formToolkit;

	/**
	 * Конструктор
	 *
	 * @param aParent
	 *            родитель
	 * @param aUserProvider
	 *            поставщик пользователя
	 */
	public UserAttributesForm(Composite aParent, FormToolkit aFormToolkit, IUserProvider aUserProvider) {
		super(aParent, SWT.NONE);
		Objects.requireNonNull(aUserProvider);
		Objects.requireNonNull(aFormToolkit);
		_userProvider = aUserProvider;
		_formToolkit = aFormToolkit;
		create();
	}

	/**
	 * Создает пользовательский интерфейс
	 */
	private void create() {
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		setLayout(mainLayout);
		User user = _userProvider.getUser();
		Objects.requireNonNull(user);
		String fio = String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getPatronymic());
		Label fioLabel = _formToolkit.createLabel(this, fio);
		fioLabel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));

		createHolidaysQuantityLabel(this).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

	}

	/**
	 * Создает строку "Количество отгулов"
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createHolidaysQuantityLabel(Composite aParent) {
		User user = _userProvider.getUser();
		Composite main = _formToolkit.createComposite(aParent);
		int columns = 2;
		int outHQ = user.getOutgoingHolidaysQuantity();
		boolean needOutHQ = outHQ != 0;

		int inHQ = user.getIncomingHolidaysQuantity();
		boolean needInHQ = inHQ != 0;

		if (needInHQ) {
			columns++;
		}
		if (needOutHQ) {
			columns++;
		}

		GridLayout mainLayout = new GridLayout(columns, false);
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		main.setLayout(mainLayout);

		Label holidaysQuantityLabel = _formToolkit.createLabel(main, Messages.holidaysQuantity);
		holidaysQuantityLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		int hq = user.getHolidaysQuantity();
		_formToolkit.createHyperlink(main, String.valueOf(hq), SWT.NONE);

		if (needOutHQ) {
			Hyperlink outHQLink = _formToolkit.createHyperlink(main, String.format("(-%s)", outHQ), SWT.NONE);
			outHQLink.addHyperlinkListener(new HyperlinkAdapter() {
			});
		}

		if (needInHQ) {
			_formToolkit.createHyperlink(main, String.format("(+%s)", inHQ), SWT.NONE);
		}

		return main;
	}

	/**
	 * Поставщик пользователя
	 *
	 */
	public static interface IUserProvider {
		/**
		 * Возвращает пользователя
		 *
		 * @return пользователь
		 */
		public User getUser();
	}

}
