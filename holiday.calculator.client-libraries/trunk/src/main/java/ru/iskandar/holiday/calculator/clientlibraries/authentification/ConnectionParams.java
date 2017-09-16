package ru.iskandar.holiday.calculator.clientlibraries.authentification;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

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

	public boolean isEmpty() {
		return (getPassword() == null) || getPassword().isEmpty() || (getUser() == null) || getUser().isEmpty()
				|| (getServerHost() == null) || getServerHost().isEmpty();

	}

	public boolean ping() {
		String host = getServerHost();
		if ((host == null) || host.isEmpty()) {
			return false;
		}
		return ping(getServerHost(), 8080, 2000);
	}

	private boolean ping(String host, int port, int timeout) {
		Objects.requireNonNull(host, "Не указан пингуемый хост");
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(host, port), timeout);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
