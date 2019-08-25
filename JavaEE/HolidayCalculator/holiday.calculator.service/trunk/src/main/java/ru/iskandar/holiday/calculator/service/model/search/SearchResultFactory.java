package ru.iskandar.holiday.calculator.service.model.search;

import org.elasticsearch.action.search.SearchResponse;

public class SearchResultFactory {

	public ISearchResult createSearchResult(SearchResponse aResponse, String aSearchText) {
		return new SearchResultImpl(aResponse, aSearchText);
	}

}
