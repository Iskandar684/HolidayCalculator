package ru.iskandar.holiday.calculator.service.model.search;

import java.io.Serializable;
import java.util.List;

/**
 * Результат поиска.
 */
public interface ISearchResult extends Serializable {

	/**
	 * Текст поиска.
	 *
	 * @return текст поиска
	 */
	String getSearchString();

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