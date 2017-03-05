package ru.iskandar.holiday.calculator.dataconnection;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Поставщик контекста
 */
public class ContextProvider {

	/** Экземпляр класса */
	private static final ContextProvider INSTANCE = new ContextProvider();

	/**
	 * Конструктор
	 */
	private ContextProvider() {
	}

	/**
	 * Возвращает контекст
	 * 
	 * @return контекст
	 * @throws NamingException
	 *             ошибка создания контекста
	 */
	public InitialContext getInitialContext() throws NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				org.jboss.naming.remote.client.InitialContextFactory.class.getName());
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		props.put(Context.SECURITY_PRINCIPAL, "testuser");
		props.put(Context.SECURITY_CREDENTIALS, "testpassword");
		props.put("jboss.naming.client.ejb.context", true);

		InitialContext context = new InitialContext(props);
		return context;
	}

	/**
	 * Возвращает экземпляр класса
	 *
	 * @return экземпляр класса
	 */
	public static ContextProvider getInstance() {
		return INSTANCE;
	}

}
