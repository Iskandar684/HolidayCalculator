package ru.iskandar.holiday.calculator.ui.menu.handlers;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginAlreadyExistException;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Диалог аутентификации
 */
public class CreateUserDialog extends TitleAreaDialog {

	private final HolidayCalculatorModelProvider _modelProvider;

	private NewUserEntry _newUserEntry;

	/**
	 * Конструктор
	 */
	public CreateUserDialog(Shell aParentShell, HolidayCalculatorModelProvider aConnectionParams) {
		super(aParentShell);
		Objects.requireNonNull(aConnectionParams);
		_modelProvider = aConnectionParams;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(Composite aParent) {
		getShell().setText(Messages.createUserDialogTitle);

		super.createDialogArea(aParent);
		Composite main = new Composite(aParent, SWT.NONE);
		final int columns = 2;
		GridLayout mainLayout = new GridLayout(columns, false);
		final int marginWidth = 20;
		mainLayout.marginWidth = marginWidth;
		main.setLayout(mainLayout);

		Label firstNameLabel = new Label(main, SWT.NONE);
		firstNameLabel.setText(Messages.firstNameLabel);
		final Text firstNameText = new Text(main, SWT.NONE);
		firstNameText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String firstName = getNewUserEntry().getFirstName();
		firstName = firstName != null ? firstName : "";
		firstNameText.setText(firstName);
		firstNameText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				getNewUserEntry().setFirstName(firstNameText.getText());
				updateOkButton();
			}
		});

		Label lastNameLabel = new Label(main, SWT.NONE);
		lastNameLabel.setText(Messages.lastNameLabel);
		final Text lastNameText = new Text(main, SWT.NONE);
		lastNameText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String lastName = getNewUserEntry().getLastName();
		lastName = lastName != null ? lastName : "";
		lastNameText.setText(lastName);
		lastNameText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				getNewUserEntry().setLastName(lastNameText.getText());
				updateOkButton();
			}
		});

		Label patronymicLabel = new Label(main, SWT.NONE);
		patronymicLabel.setText(Messages.patronymicLabel);
		final Text patronymicText = new Text(main, SWT.NONE);
		patronymicText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String patronymic = getNewUserEntry().getPatronymic();
		patronymic = patronymic != null ? patronymic : "";
		patronymicText.setText(patronymic);
		patronymicText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				getNewUserEntry().setPatronymic(patronymicText.getText());
				updateOkButton();
			}
		});

		Label loginLabel = new Label(main, SWT.NONE);
		loginLabel.setText(Messages.loginLabel);
		final Text loginText = new Text(main, SWT.NONE);
		loginText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String login = getNewUserEntry().getLogin();
		login = login != null ? login : "";
		loginText.setText(login);
		loginText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				getNewUserEntry().setLogin(loginText.getText());
				updateOkButton();
			}
		});

		Label passwordLabel = new Label(main, SWT.NONE);
		passwordLabel.setText(Messages.passwordLabel);
		final Text passwordText = new Text(main, SWT.PASSWORD);
		String password = getNewUserEntry().getPassword();
		password = password != null ? password : "";
		passwordText.setText(password);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		passwordText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				getNewUserEntry().setPassword(passwordText.getText());
				updateOkButton();
			}
		});
		updateOkButton();
		return main;
	}

	/**
	 * @return the newUserEntry
	 */
	protected NewUserEntry getNewUserEntry() {
		if (_newUserEntry == null) {
			_newUserEntry = new NewUserEntry();
			_newUserEntry.setEmploymentDate(new Date());
		}
		return _newUserEntry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createButtonsForButtonBar(Composite aParent) {
		super.createButtonsForButtonBar(aParent);
		updateOkButton();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void okPressed() {
		NewUserEntry entry = getNewUserEntry();
		if (entry.isEmpty()) {
			MessageDialog.openWarning(getShell(), getShell().getText(), Messages.creatingUserIsEmpty);
			return;
		}
		try {
			_modelProvider.getModel().createUser(entry, new HashSet<>());
		} catch (UserByLoginAlreadyExistException e) {
			MessageDialog.openError(getShell(), getShell().getText(),
					NLS.bind(Messages.userByLoginAlreadyExistError, entry.getLogin()));
			return;
		}
		super.okPressed();
	}

	/**
	 * Обновляет состояние кнопки OK
	 */
	private void updateOkButton() {
		Button okButton = getButton(OK);
		if (okButton != null) {
			okButton.setEnabled(isOk());
		}
	}

	private boolean isOk() {
		return !getNewUserEntry().isEmpty();
	}

}
