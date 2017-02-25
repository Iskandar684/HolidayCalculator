package ru.iskandar.holiday.calculator.ui;

/**
 * Загрузчик
 */
public interface ILoadingProvider {

	/**
	 * Возвращает статус загрузки
	 *
	 * @return статус загрузки
	 */
	public LoadStatus getLoadStatus();

	/**
	 * Статус загрузки модели
	 */
	public static enum LoadStatus {
		/** В процессе загрузки */
		LOADING,
		/** Загружена */
		LOADED,
		/** Ошибка загрузки */
		LOAD_ERROR
	}

}
