package ru.iskandar.holiday.calculator.dataconnection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelInitException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;

/**
 * Коннектор
 */
public class ClientConnector {

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
			model.init(ctx);
		} catch (HolidayCalculatorModelInitException e) {
			throw new ConnectionException("Ошибка инициализации модели учета отгулов", e);
		}
		return model;

	}

}
