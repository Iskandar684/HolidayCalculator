package ru.iskandar.holiday.calculator.service.ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelException;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelFactory;

/**
 * Сервис учета отгулов
 */
@Stateless
@Remote(IHolidayCalculatorRemote.class)
public class HolidayCalculatorBean implements IHolidayCalculatorRemote {
	/***/
	@Resource
	private SessionContext _context;

	/**
	 * {@inheritDoc}
	 */
	@PermitAll
	@Override
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelException {
		System.out.println(_context.getCallerPrincipal().getClass());
		System.out.println("callerPrincipal " + _context.getCallerPrincipal());
		try {
			System.out.println("fromProp " + new InitialContext().getEnvironment().get(Context.SECURITY_PRINCIPAL));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HolidayCalculatorModelFactory().create();
	}

}