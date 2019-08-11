package ru.iskandar.holiday.calculator.dataconnection;

import java.util.List;

public interface ISearchResult {

	/**
	 * Возвращает совпадения.
	 *
	 * @return совпадаения.
	 */
	List<ISearchHit> getHits();

	/**
	 * Возвращает время поиска в мс.
	 *
	 * @return время поиска
	 */
	long getTookInMillis();

	/**
	 * Возвращает общее количество совпадений.
	 * 
	 * @return общее количество совпадений
	 */
	long getTotalHitsCount();

}
