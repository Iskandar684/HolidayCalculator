package ru.iskandar.holiday.calculator.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Сообщения
 */
public class Messages extends NLS {

	/** Путь к ресурсам */
	private static final String BUNDLE_NAME = "ru.iskandar.holiday.calculator.ui.Messages"; //$NON-NLS-1$

	/** Количество отгулов */
	public static String holidaysQuantity;

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
