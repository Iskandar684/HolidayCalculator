package ru.iskandar.holiday.calculator.ui.takeleave;

import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.service.model.ServiceLookupException;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.TakeLeaveStatementBuilder;
import ru.iskandar.holiday.calculator.ui.Activator;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Контроллер кнопки подачи заявления на отпуск
 */
public class SendLeaveStatementButtonPM {

	/** Кнопка */
	private final Button _button;

	/** Поставщик модели */
	private final TakeLeaveStatementBuilder _statementBuilder;

	/**
	 * Конструктор
	 */
	public SendLeaveStatementButtonPM(Button aButton, final TakeLeaveStatementBuilder aStatementBuilder) {
		_button = aButton;
		_statementBuilder = aStatementBuilder;
		aButton.addSelectionListener(new SelectionHandler());
		update();
	}

	/**
	 * Обработчик нажатия кнопки
	 */
	private class SelectionHandler extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetSelected(SelectionEvent aE) {
			if (_statementBuilder.getDays().isEmpty()) {
				MessageDialog.openWarning(_button.getShell(), Messages.noDateSelectedForLeaveStatementDialogTitle,
						Messages.noDateSelectedForLeaveStatementDialogText);
				return;
			}
			try {
				_statementBuilder.sendLeaveStatement();
			} catch (StatementAlreadySendedException e) {
				Statement earlySendedStatement = e.getEarlySendenStatement();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String mess = NLS.bind(Messages.leaveStatementAlreadySendedDialogTextForDays,
						dateFormatter.format(earlySendedStatement.getCreateDate()));
				MessageDialog.openWarning(_button.getShell(), Messages.holidayStatementAlreadySendedDialogTitle, mess);
			} catch (ServiceLookupException e) {
				StatusManager.getManager()
						.handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
								Messages.holidayStatementSendErrorBecauseCannotLookupRemoteServiceDialogText, e),
								StatusManager.LOG);
				MessageDialog.openError(_button.getShell(), Messages.holidayStatementAlreadySendedDialogTitle,
						Messages.holidayStatementSendErrorBecauseCannotLookupRemoteServiceDialogText);
			} finally {
				update();
			}
		}
	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		// _button.setEnabled(_statementBuilder.canSendHolidayStatement());
	}

}
