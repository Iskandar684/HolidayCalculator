package ru.iskandar.holiday.calculator.clientlibraries.authentification;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class AuthentificationDialogMessages extends NLS {

	/** Путь к ресурсам */
	private static final String BUNDLE_NAME = "ru.iskandar.holiday.calculator.clientlibraries.authentification.authentificationDialog"; //$NON-NLS-1$

	public static String loginLabel;

	public static String passwordLabel;

	public static String title;

	public static String description;

	public static String serverLabel;

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, AuthentificationDialogMessages.class);
	}

}
