package ru.iskandar.holiday.calculator.ui.user;

import org.eclipse.osgi.util.NLS;

/**
 * Сообщения
 */
public class UsersTableProperties extends NLS {

	/** Путь к ресурсам */
	private static final String BUNDLE_NAME = "ru.iskandar.holiday.calculator.ui.user.usersTable"; //$NON-NLS-1$

	public static String firstNameLabel;

	public static String lastNameLabel;

	public static String patronymicLabel;

	public static String loginLabel;

	public static String employmentDate;

	public static String note;

	public static String usersTableLoadingText;

	public static String usersTableLoadErrorText;

	/** Пустая строка */
	public static final String EMPTY = "";

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, UsersTableProperties.class);
	}

	private UsersTableProperties() {
	}

}
