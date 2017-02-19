package ru.iskandar.holiday.calculator.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Сообщения
 */
public class Messages extends NLS {

	/** Путь к ресурсам */
	private static final String BUNDLE_NAME = "ru.iskandar.holiday.calculator.ui.messages"; //$NON-NLS-1$

	/** Количество отгулов */
	public static String holidaysQuantity;

	/** Количество дней отпуска */
	public static String leaveCount;

	/** Дата начала следующего периода */
	public static String nextLeaveStartDate;

	/** Наименование кнопки подачи заявления на отгул */
	public static String getHoliday;

	/** Наименование кнопки подачи заявления на отпуск */
	public static String getLeave;

	/** Наименование кнопки подачи заявления на отзыв */
	public static String makeRecall;

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
