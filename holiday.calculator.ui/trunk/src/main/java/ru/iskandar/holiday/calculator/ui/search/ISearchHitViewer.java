package ru.iskandar.holiday.calculator.ui.search;

import ru.iskandar.holiday.calculator.service.model.search.ISearchHit;

/**
 * Зритель результата поиска.
 */
public interface ISearchHitViewer {

	boolean canView(ISearchHit aSearchHit);

	void view(ISearchHit aSearchHit);

}
