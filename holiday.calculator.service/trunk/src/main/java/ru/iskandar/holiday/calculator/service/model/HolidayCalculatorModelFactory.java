package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

import ru.iskandar.holiday.calculator.service.ejb.ICurrentUserServiceLocal;

/**
 * Фабрика создания модели учета отгулов
 */
public class HolidayCalculatorModelFactory {

	/** Сервис работы с текущим пользователем */
	private final ICurrentUserServiceLocal _currentUserServiceLocal;

	/**
	 * Конструктор
	 */
	public HolidayCalculatorModelFactory(ICurrentUserServiceLocal aCurrentUserServiceLocal) {
		Objects.requireNonNull(aCurrentUserServiceLocal);
		_currentUserServiceLocal = aCurrentUserServiceLocal;
	}

	/**
	 * Создает модель
	 *
	 * @return модель
	 */
	public HolidayCalculatorModel create() {
		User currenUser = _currentUserServiceLocal.getCurrentUser();
		return new HolidayCalculatorModel(currenUser);
	}

}
