package ru.iskandar.holiday.calculator.dataconnection.internal;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import ru.iskandar.holiday.calculator.dataconnection.ISearchHit;
import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;

public class SearchResultFactory {

	public ISearchResult createSearchResult(SearchResponse aResponse) {
		List<ISearchHit> hits = new ArrayList<>();
		for (SearchHit hit : aResponse.getHits()) {
			hits.add(createSearchHit(hit));
		}
		return new SearchResultImpl(hits);
	}

	private ISearchHit createSearchHit(SearchHit aSearchHit) {
		return new SearchHitImpl(aSearchHit);
	}

}
