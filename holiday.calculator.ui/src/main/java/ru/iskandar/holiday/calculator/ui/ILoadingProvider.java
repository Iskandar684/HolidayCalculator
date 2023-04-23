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

	/**
	 * Слушатель изменения статуса загрузки
	 */
	public interface ILoadListener {

		/**
		 * Обрабатывает изменения статуса загрузки
		 */
		public void loadStatusChanged();
	}

	/**
	 * Добавляет слушатель изменения статуса загрузки
	 *
	 * @param aLoadListener
	 *            слушатель изменения статуса загрузки
	 */
	public void addLoadListener(ILoadListener aLoadListener);

	/**
	 * Удаляет слушатель изменения статуса загрузки
	 *
	 * @param aLoadListener
	 *            слушатель изменения статуса загрузки
	 */
	public void removeLoadListener(ILoadListener aLoadListener);

}
