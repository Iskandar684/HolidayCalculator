package ru.iskandar.holiday.calculator.service.model;

/**
 * Поставщик сервисов
 */
public interface IServicesProvider {

	/**
	 * Возвращает сервис учета отгулов
	 *
	 * @return сервис учета отгулов
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис
	 */
	public IHolidayCalculatorService getHolidayCalculatorService();

}
