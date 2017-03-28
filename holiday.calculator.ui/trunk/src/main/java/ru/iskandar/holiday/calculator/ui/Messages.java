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

	/** Ошибка загрузки модели учета отгулов */
	public static String modelLoadError;

	/** Ошибка оповещения изменения статуса загрузки модели */
	public static String modelLoadStatusChangedNotificationError;

	/** Попробовать перезагрузить модель */
	public static String tryReloadModel;

	/** Идет загрузка модели */
	public static String modelLoading;

	/** Текст пункта меню открытия входящих заявлений */
	public static String openIncomingStatementsMenuItem;

	/** Текст пункта меню открытия входящих заявлений со счетчиком */
	public static String openIncomingStatementsMenuItemWithCount;

	/** Тест корневой меню для заявлений */
	public static String statementsRootMenuName;

	/** Тест корневой меню для заявлений со счетчиком */
	public static String statementsRootMenuNameWithCount;

	/** Текст элемента меню взять отгул */
	public static String takeHolidayMenuItem;

	/** Текст заголовка диалога заявление уже подано */
	public static String holidayStatementAlreadySendedDialogTitle;

	/** Текст диалога заявление уже подано */
	public static String holidayStatementAlreadySendedDialogTextForDay;

	/** Текст диалога заявление уже подано */
	public static String holidayStatementAlreadySendedDialogTextForDays;

	public static String statementsTableTypeColumnText;

	public static String statementsTableAuthorColumnText;

	public static String statementsTableCreateDateColumnText;

	public static String statementsTableReviewDateColumnText;

	public static String statementsTableStatusColumnText;

	/** Не рассмотрено */
	public static String notConsidered;

	/** Одобрено */
	public static String approve;

	/** Отклонено */
	public static String rejected;

	public static String holidayStatement;

	public static String recallStatement;

	public static String reviewHolidayStatementHeader;

	public static String reviewRecallStatementHeader;

	public static String approveBt;

	public static String rejectBt;

	public static String statementAlreadyConsideredDialogTitle;

	public static String statementAlreadyConsideredByYouDialogText;

	public static String statementAlreadyConsideredByOtherDialogText;

	public static String statementForConsiderNotSelectedDialogText;

	public static String statementForConsiderNotSelectedDialogTitle;

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
