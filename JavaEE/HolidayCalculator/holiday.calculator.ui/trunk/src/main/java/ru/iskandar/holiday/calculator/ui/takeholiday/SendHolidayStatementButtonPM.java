package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.text.SimpleDateFormat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import ru.iskandar.holiday.calculator.service.ejb.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Контроллер кнопки подачи заявления на отгул
 *
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
		aButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				try {
					aStatementBuilder.sendHolidayStatement();
				} catch (StatementAlreadySendedException e) {
					Statement earlySendedStatement = e.getEarlySendenStatement();
					SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					String mess;
					if (aStatementBuilder.getDays().size() == 1)
						mess = NLS.bind(Messages.holidayStatementAlreadySendedDialogTextForDay,
								dateFormatter.format(earlySendedStatement.getCreateDate()));
					else {
						mess = NLS.bind(Messages.holidayStatementAlreadySendedDialogTextForDays,
								dateFormatter.format(earlySendedStatement.getCreateDate()));
					}
					MessageDialog.openWarning(aButton.getShell(), Messages.holidayStatementAlreadySendedDialogTitle,
							mess);
				}
			}
		});
		update();
	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		_button.setEnabled(_statementBuilder.canSendHolidayStatement());
	}

}
