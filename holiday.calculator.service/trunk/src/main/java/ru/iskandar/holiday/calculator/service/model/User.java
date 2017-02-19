package ru.iskandar.holiday.calculator.service.model;

import java.util.Date;

/**
 * Пользователь
 *
 */
public class User {

	/** Имя */
	private final String _firstName;
	/** Фамилия */
	private final String _lastName;
	/** Отчество */
	private final String _patronymic;

	/**
	 * Конструктор
	 */
	protected User(String aLastName, String aFirstName, String aPatronymic) {
		_firstName = aFirstName;
		_lastName = aLastName;
		_patronymic = aPatronymic;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public String getPatronymic() {
		return _patronymic;
	}

	/**
	 * Возвращает общее количество отгулов
	 *
	 * @return количество отгулов
	 */
	public int getHolidaysQuantity() {
		// FIXME
		return 5;
	}

	/**
	 * Возвращает количество исходящих дней отгула. Это количество дней, на
	 * которое уменьшется количество общее дней отгула, после того как заявление
	 * на отгул будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отгула
	 */
	public int getOutgoingHolidaysQuantity() {
		return 1;
	}

	/**
	 * Возвращает количество приходящих отгулов. Это количество дней, на которое
	 * будет увеличино общее количество отгулов, после того как засчитают отзыв.
	 *
	 * @return количество приходящих отгулов
	 */
	public int getIncomingHolidaysQuantity() {
		return 7;
	}

	/**
	 * Возвращает количество не использованных дней отпуска в этом периоде
	 *
	 * @return количество дней
	 */
	public int getLeaveCount() {
		return 28;
	}

	/**
	 * Возвращает количество исходящих дней отпуска. Это количество дней, на
	 * которое уменьшется количество дней отпуска в этом периоде, после того как
	 * заявление на отпуск будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отпуска.
	 */
	public int getOutgoingLeaveCount() {
		return 14;
	}

	/**
	 * Возвращает дату начала следующего периода
	 *
	 * @return дата начала следующего периода
	 */
	public Date getNextLeaveStartDate() {
		return new Date();
	}

}
