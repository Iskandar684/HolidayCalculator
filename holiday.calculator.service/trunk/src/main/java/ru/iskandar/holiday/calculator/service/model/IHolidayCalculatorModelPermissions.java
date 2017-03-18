package ru.iskandar.holiday.calculator.service.model;

/**
 * @author Искандар
 *
 */
public interface IHolidayCalculatorModelPermissions {

	/**
	 * Возвращает возможность рассматривать заявления
	 * 
	 * @return {@code true}, если пользователю разрешено рассматривать заявления
	 */
	public boolean canConsider();
}
