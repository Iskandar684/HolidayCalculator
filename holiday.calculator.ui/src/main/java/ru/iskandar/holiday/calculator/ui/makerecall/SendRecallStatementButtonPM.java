package ru.iskandar.holiday.calculator.ui.makerecall;

import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.service.model.MakeRecallStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.ServiceLookupException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.ui.Activator;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Контроллер кнопки подачи заявления на отпуск
 */
public class SendRecallStatementButtonPM {

	/** Кнопка */
	private final Button _button;

	/** Поставщик модели */
	private final MakeRecallStatementBuilder _statementBuilder;

	/**
	 * Конструктор
	 */
	public SendRecallStatementButtonPM(Button aButton, final MakeRecallStatementBuilder aStatementBuilder) {
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
				MessageDialog.openWarning(_button.getShell(), Messages.noDateSelectedForRecallStatementDialogTitle,
						Messages.noDateSelectedForRecallStatementDialogText);
				return;
			}

			try {
				_statementBuilder.sendRecallStatement();
			} catch (StatementAlreadySendedException e) {
				Statement<?> earlySendedStatement = e.getEarlySendenStatement();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String mess = NLS.bind(Messages.recallStatementAlreadySendedDialogTextForDays,
						dateFormatter.format(earlySendedStatement.getCreateDate()));
				MessageDialog.openWarning(_button.getShell(), Messages.recallStatementAlreadySendedDialogTitleForDays,
						mess);
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
		_button.setEnabled(_statementBuilder.canSendRecallStatement());
	}

}
