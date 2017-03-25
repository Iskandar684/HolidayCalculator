/**
 *
 */
package ru.iskandar.holiday.calculator.service.ejb;

/**
 * @author Искандар
 *
 */
public class HolidayCalculatorException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 6418817234419893147L;

	public HolidayCalculatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public HolidayCalculatorException(String message) {
		super(message);
	}

}
