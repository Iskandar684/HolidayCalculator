package ru.iskandar.holiday.calculator.clientlibraries.authentification;

import java.util.Objects;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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

/**
 * Диалог аутентификации
 */
public class AuthentificationDialog extends TitleAreaDialog {

	private final ConnectionParams _model;

	/**
	 * Конструктор
	 */
	public AuthentificationDialog(Shell aParentShell, ConnectionParams aConnectionParams) {
		super(aParentShell);
		Objects.requireNonNull(aConnectionParams);
		_model = aConnectionParams;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(Composite aParent) {
		getShell().setText(AuthentificationDialogMessages.title);
		setMessage(AuthentificationDialogMessages.description, IMessageProvider.INFORMATION);
		super.createDialogArea(aParent);
		Composite main = new Composite(aParent, SWT.NONE);
		final int columns = 2;
		GridLayout mainLayout = new GridLayout(columns, false);
		final int marginWidth = 20;
		mainLayout.marginWidth = marginWidth;
		main.setLayout(mainLayout);

		Label serverLabel = new Label(main, SWT.NONE);
		serverLabel.setText(AuthentificationDialogMessages.serverLabel);
		final Text serverText = new Text(main, SWT.NONE);
		serverText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String server = _model.getServerHost();
		server = server != null ? server : "";
		serverText.setText(server);
		serverText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				_model.setServer(serverText.getText());
				updateOkButton();
			}
		});

		Label loginLabel = new Label(main, SWT.NONE);
		loginLabel.setText(AuthentificationDialogMessages.loginLabel);
		final Text loginText = new Text(main, SWT.NONE);
		loginText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String login = _model.getUser();
		login = login != null ? login : "";
		loginText.setText(login);
		loginText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void modifyText(ModifyEvent aE) {
				_model.setUser(loginText.getText());
				updateOkButton();
			}
		});

		Label passwordLabel = new Label(main, SWT.NONE);
		passwordLabel.setText(AuthentificationDialogMessages.passwordLabel);
		final Text passwordText = new Text(main, SWT.PASSWORD);
		String password = _model.getPassword();
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
				_model.setPassword(passwordText.getText());
				updateOkButton();
			}
		});
		updateOkButton();
		return main;
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
	 * Обновляет состояние кнопки OK
	 */
	private void updateOkButton() {
		Button okButton = getButton(OK);
		if (okButton != null) {
			okButton.setEnabled(isOk());
		}
	}

	private boolean isOk() {
		return !_model.isEmpty();
	}

}
