package ru.iskandar.holiday.calculator.dataconnection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.clientlibraries.Activator;
import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote;
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
		IHolidayCalculatorRemote helloWorldRemote = (IHolidayCalculatorRemote) obj;
		HolidayCalculatorModel model;
		try {
			model = helloWorldRemote.loadHolidayCalculatorModel();
		} catch (HolidayCalculatorModelLoadException e) {
			throw new ConnectionException("Ошибка создания модели учета отгулов", e);
		}
		try {
			model.init(ctx, _logger);
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
