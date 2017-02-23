package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;

/**
 * Модель учета отгулов
 *
 */
public class HolidayCalculatorModel implements Serializable {

	/**
	 * Индентификатор для сериализации
	 */
	private static final long serialVersionUID = -2279698461127019581L;

	/**
	 * Конструктор
	 */
	HolidayCalculatorModel() {
	}

	/**
	 * Возвращает текущего пользователя
	 *
	 * @return текущий пользователь
	 *
	 */
	public User getCurrentUser() {
		// TODO
		return new User("Анисимов", "Олег", "Артёмович");
	}

	/**
	 * Создает формирователь заявления на отгул
	 *
	 * @return формирователь заявления на отгул
	 */
	public TakeHolidayStatementBuilder createHolidayStatementBuilder() {
		if (!canCreateHolidayStatementBuilder()) {
			throw new IllegalStateException("Формирование заявления на отгул запрещено");
		}
		return new TakeHolidayStatementBuilder();
	}

	/**
	 * Возвращает возможность формирования заявления на отгул
	 *
	 * @return возможность формирования заявления на отгул
	 */
	public boolean canCreateHolidayStatementBuilder() {
		return true;
	}

}
