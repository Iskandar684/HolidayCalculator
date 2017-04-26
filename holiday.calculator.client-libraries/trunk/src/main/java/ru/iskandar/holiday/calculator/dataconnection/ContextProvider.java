package ru.iskandar.holiday.calculator.dataconnection;

import java.util.Objects;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.iskandar.holiday.calculator.clientlibraries.authentification.ConnectionParams;

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
	 * @param aProgramArgs
	 *            аргументы программы
	 * @return контекст
	 * @throws NamingException
	 *             ошибка создания контекста
	 * @throws InvalidConnectionParamsException
	 *             если невалидные аргументы программы
	 */
	public InitialContext getInitialContext(ConnectionParams aProgramArgs) throws NamingException, InvalidConnectionParamsException {
		Objects.requireNonNull(aProgramArgs);

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				org.jboss.naming.remote.client.InitialContextFactory.class.getName());
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		String server = aProgramArgs.getServerHost();
		if ((server == null) || server.isEmpty()) {
			throw new InvalidConnectionParamsException("Не указан адрес сервера");
		}
		String user = aProgramArgs.getUser();
		if ((user == null) || user.isEmpty()) {
			throw new InvalidConnectionParamsException("Не указан пользователь");
		}
		String password = aProgramArgs.getPassword();
		if ((password == null) || password.isEmpty()) {
			throw new InvalidConnectionParamsException("Не указан пароль");
		}

		props.put(Context.PROVIDER_URL, String.format("http-remoting://%s:8080", server));
		props.put(Context.SECURITY_PRINCIPAL, user);
		props.put(Context.SECURITY_CREDENTIALS, password);
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
