package ru.iskandar.holiday.calculator.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Сообщения
 */
public class Messages extends NLS {

	/** Путь к ресурсам */
	private static final String BUNDLE_NAME = "ru.iskandar.holiday.calculator.ui.messages"; //$NON-NLS-1$

	/** Пустая строка */
	public static final String EMPTY = "";

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

	/** Заголовок формы подачи заявления на отгул */
	public static String getHolidayFormHeader;

	/** Наименование кнопки выбора типа отгула - за счет отгулов */
	public static String getHolidayByHolidayDays;

	/** Наименование кнопки выбора типа отгула - за свой счет */
	public static String getHolidayByOwn;

	/** Наименование кнопки отправки заявления на отгул */
	public static String takeHoliday;

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
