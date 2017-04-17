package ru.iskandar.holiday.calculator.ui.takeholiday;

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
import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.ui.Activator;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Контроллер кнопки подачи заявления на отгул
 */
public class SendHolidayStatementButtonPM {

	/** Кнопка */
	private final Button _button;

	/** Формирователь заявления */
	private final TakeHolidayStatementBuilder _statementBuilder;

	/**
	 * Конструктор
	 */
	public SendHolidayStatementButtonPM(Button aButton, final TakeHolidayStatementBuilder aStatementBuilder) {
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
				MessageDialog.openWarning(_button.getShell(), Messages.noDateSelectedForHolidayStatementDialogTitle,
						Messages.noDateSelectedForHolidayStatementDialogText);
				return;
			}
			try {
				_statementBuilder.sendHolidayStatement();
			} catch (StatementAlreadySendedException e) {
				Statement earlySendedStatement = e.getEarlySendenStatement();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String mess;
				if (_statementBuilder.getDays().size() == 1)
					mess = NLS.bind(Messages.holidayStatementAlreadySendedDialogTextForDay,
							dateFormatter.format(earlySendedStatement.getCreateDate()));
				else {
					mess = NLS.bind(Messages.holidayStatementAlreadySendedDialogTextForDays,
							dateFormatter.format(earlySendedStatement.getCreateDate()));
				}
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
		_button.setEnabled(_statementBuilder.canSendHolidayStatement());
	}

}
