package ru.iskandar.holiday.calculator.clientlibraries.authentification;

/**
 * Поставщик аргументов программы
 */
public class ConnectionParams {

	private static final ConnectionParams INSTANCE = new ConnectionParams();

	/**
	 * Конструктор
	 */
	private ConnectionParams() {
	}

	public String getServerHost() {
		return System.getProperty("server");
	}

	String setServer(String aServer) {
		return System.setProperty("server", aServer);
	}

	public String getUser() {
		return System.getProperty("user");
	}

	public String getPassword() {
		return System.getProperty("password");
	}

	String setUser(String aUser) {
		return System.setProperty("user", aUser);
	}

	String setPassword(String aPassord) {
		return System.setProperty("password", aPassord);
	}

	public static ConnectionParams getInstance() {
		return INSTANCE;
	}

}
