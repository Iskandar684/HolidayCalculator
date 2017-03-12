package ru.iskandar.holiday.calculator.dataconnection;

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

	public String getUser() {
		return System.getProperty("user");
	}

	public String getPassword() {
		return System.getProperty("password");
	}

	public static ConnectionParams getInstance() {
		return INSTANCE;
	}

}
