package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote;

/**
 * Поставщик удаленных сервисов
 */
public class RemoteServicesProvider implements IServicesProvider {

	/** Контекст */
	private final InitialContext _initialContext;

	/**
	 * Конструктор
	 *
	 * @param aInitialContext
	 *            контекст
	 */
	public RemoteServicesProvider(InitialContext aInitialContext) {
		Objects.requireNonNull(aInitialContext);
		_initialContext = aInitialContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IHolidayCalculatorRemote getHolidayCalculatorService() {
		IHolidayCalculatorRemote service;
		try {
			service = (IHolidayCalculatorRemote) _initialContext.lookup(IHolidayCalculatorRemote.JNDI_NAME);
		} catch (NamingException e) {
			throw new ServiceLookupException("Ошибка получения сервиса учета отгулов", e);
		}
		return service;
	}

}
