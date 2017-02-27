package ru.iskandar.holiday.calculator.ui.dataconnection;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;

/**
 * Коннектор
 */
public class ClientConnector {

	public HolidayCalculatorModel loadModel() throws ConnectionException {
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
		// env.put(Context.PROVIDER_URL, "remote://127.0.0.1:4447");

		env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		env.put(Context.SECURITY_PRINCIPAL, "testuser");
		env.put(Context.SECURITY_CREDENTIALS, "testpassword");

		env.put("jboss.naming.client.ejb.context", true);
		try {
			InitialContext ctx = new InitialContext(env);
			Object obj = ctx.lookup(IHolidayCalculatorRemote.JNDI_NAME);
			IHolidayCalculatorRemote helloWorldRemote = (IHolidayCalculatorRemote) obj;
			HolidayCalculatorModel model = helloWorldRemote.loadHolidayCalculatorModel();
			return model;
		} catch (Exception e) {
			throw new ConnectionException("Ошибка загрузки модели учета отгулов", e);
		}
	}

}
