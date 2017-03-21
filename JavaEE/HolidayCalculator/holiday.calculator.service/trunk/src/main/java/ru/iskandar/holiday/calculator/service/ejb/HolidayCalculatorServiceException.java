/**
 *
 */
package ru.iskandar.holiday.calculator.service.ejb;

/**
 * @author Искандар
 *
 */
public class HolidayCalculatorServiceException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 6418817234419893147L;

	public HolidayCalculatorServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public HolidayCalculatorServiceException(String message) {
		super(message);
	}

}
