package ru.iskandar.holiday.calculator.ui.statement;

import org.eclipse.osgi.util.NLS;

/**
 * Сообщения
 */
public class StatementsTableProperties extends NLS {

	/** Путь к ресурсам */
	private static final String BUNDLE_NAME = "ru.iskandar.holiday.calculator.ui.statement.statementsTable"; //$NON-NLS-1$

	/** Пустая строка */
	public static final String EMPTY = "";

	public static String statementsTableTypeColumnText;

	public static String statementsTableAuthorColumnText;

	public static String statementsTableCreateDateColumnText;

	public static String statementsTableReviewDateColumnText;

	public static String statementsTableStatusColumnText;

	public static String statementsTableConsiderColumnText;

	public static String statementsTableConsiderDateColumnText;

	public static String statementsTableContentColumnText;

	static {
		// инициализация ресурсов бандла
		NLS.initializeMessages(BUNDLE_NAME, StatementsTableProperties.class);
	}

	private StatementsTableProperties() {
	}

}
