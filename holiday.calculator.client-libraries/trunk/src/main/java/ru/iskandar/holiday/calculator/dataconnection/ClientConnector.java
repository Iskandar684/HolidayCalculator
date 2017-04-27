package ru.iskandar.holiday.calculator.dataconnection;

import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.clientlibraries.Activator;
import ru.iskandar.holiday.calculator.clientlibraries.authentification.AuthentificationDialog;
import ru.iskandar.holiday.calculator.clientlibraries.authentification.ConnectionParams;
import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote;
import ru.iskandar.holiday.calculator.service.model.ClientId;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel.IHolidayCalculatorModelLogger;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelInitException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;

/**
 * Коннектор
 */
public class ClientConnector {

	/** Логгер */
	private final Logger _logger = new Logger();

	/** Идентификатор клиента */
	private final ClientId _clientId = ClientId.fromUUID(UUID.randomUUID());

	private void checkConnection(ConnectionParams aParams) throws ConnectionException {
		InitialContext ctx;
		try {
			ctx = ContextProvider.getInstance().getInitialContext(aParams);
		} catch (NamingException e) {
			throw new ConnectionException("Ошибка создания контекста", e);
		} catch (InvalidConnectionParamsException e) {
			throw new ConnectionException("Указаны невалидные агрументы программы", e);
		}
		Object obj;
		try {
			obj = ctx.lookup(IHolidayCalculatorRemote.JNDI_NAME);
		} catch (NamingException e) {
			throw new ConnectionException("Ошибка получения сервиса учета отгулов", e);
		}
		IHolidayCalculatorRemote remoteService = (IHolidayCalculatorRemote) obj;
		try {
			remoteService.checkAuthentification();
		} catch (Exception e) {
			throw new ConnectionException("Аутентификация не пройдена", e);
		}
	}

	public void authenticate() {
		ClientConnector connector = new ClientConnector();
		ConnectionParams params = ConnectionParams.getInstance();
		if (params.isEmpty()) {
			AuthentificationDialog dialog = new AuthentificationDialog(Display.getDefault().getActiveShell(), params);
			if (IDialogConstants.OK_ID != dialog.open()) {
				cancelAuthenticate();
				return;
			}
		}
		try {
			connector.checkConnection(params);
		} catch (Exception e) {
			StatusManager.getManager()
					.handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Ошибка аутентификации", e));
			AuthentificationDialog dialog = new AuthentificationDialog(Display.getDefault().getActiveShell(), params);
			if (IDialogConstants.OK_ID == dialog.open()) {
				authenticate();
			} else {
				cancelAuthenticate();
				return;
			}
		}
	}

	private void cancelAuthenticate() {
		PlatformUI.getWorkbench().close();
		StatusManager.getManager()
				.handle(new Status(IStatus.WARNING, Activator.PLUGIN_ID, "Пользователь отменил аутентификацию"));
	}

	/**
	 * Загружает модель
	 *
	 * @return модель
	 * @throws ConnectionException
	 *             ошибка загрузки модели
	 */
	public HolidayCalculatorModel loadModel() throws ConnectionException {
		ConnectionParams args = ConnectionParams.getInstance();

		InitialContext ctx;
		try {
			ctx = ContextProvider.getInstance().getInitialContext(args);
		} catch (NamingException e) {
			throw new ConnectionException("Ошибка создания контекста", e);
		} catch (InvalidConnectionParamsException e) {
			throw new ConnectionException("Указаны невалидные агрументы программы", e);
		}
		Object obj;
		try {
			obj = ctx.lookup(IHolidayCalculatorRemote.JNDI_NAME);
		} catch (NamingException e) {
			throw new ConnectionException("Ошибка получения сервиса учета отгулов", e);
		}
		IHolidayCalculatorRemote remote = (IHolidayCalculatorRemote) obj;
		HolidayCalculatorModel model;
		try {
			model = remote.loadHolidayCalculatorModel();
		} catch (HolidayCalculatorModelLoadException e) {
			throw new ConnectionException("Ошибка создания модели учета отгулов", e);
		}
		try {
			model.init(ctx, _logger, _clientId);
		} catch (HolidayCalculatorModelInitException e) {
			throw new ConnectionException("Ошибка инициализации модели учета отгулов", e);
		}

		return model;
	}

	/**
	 * Логгер
	 */
	private static class Logger implements IHolidayCalculatorModelLogger {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void logError(String aMessage, Exception aException) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, aMessage, aException));
		}

	}

}
