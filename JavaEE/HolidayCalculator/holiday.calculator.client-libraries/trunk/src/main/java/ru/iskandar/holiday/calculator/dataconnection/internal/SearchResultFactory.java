package ru.iskandar.holiday.calculator.dataconnection.internal;

import org.elasticsearch.action.search.SearchResponse;

import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;

public class SearchResultFactory {

	public ISearchResult createSearchResult(SearchResponse aResponse, String aSearchText) {
		return new SearchResultImpl(aResponse, aSearchText);
	}

}
