package ru.iskandar.holiday.calculator.ui.document;

/**
 *
 */
public class HTMLContent {

	public static final HTMLContent EMPTY = new HTMLContent(new byte[0]);

	private final byte[] _content;

	/**
	 * Конструктор
	 */
	public HTMLContent(byte[] aContent) {
		_content = aContent;
	}

	public byte[] getContent() {
		return _content;
	}

}
