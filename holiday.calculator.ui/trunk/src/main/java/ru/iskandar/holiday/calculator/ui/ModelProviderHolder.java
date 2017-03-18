package ru.iskandar.holiday.calculator.ui;

/**
 * Хранитель поставщика модели
 */
public class ModelProviderHolder {

	/** Экземпляр класса */
	private static final ModelProviderHolder INSTANCE = new ModelProviderHolder();

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider = new HolidayCalculatorModelProvider();

	/**
	 * Возвращает экземпляр класса
	 *
	 * @return экземпляр класса
	 */
	public static ModelProviderHolder getInstance() {
		return INSTANCE;
	}

	/**
	 * Возвращает поставщик модели
	 * 
	 * @return поставщик модели
	 */
	public HolidayCalculatorModelProvider getModelProvider() {
		return _modelProvider;
	}

}
