package ru.iskandar.holiday.calculator.dataconnection.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import ru.iskandar.holiday.calculator.dataconnection.ISearchHit;
import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;

class SearchResultImpl implements ISearchResult {

	private final List<ISearchHit> _hits = new ArrayList<>();

	/** Ответ запроса поиска */
	private final SearchResponse _response;

	SearchResultImpl(SearchResponse aResponse) {
		_response = Objects.requireNonNull(aResponse);
		for (SearchHit hit : aResponse.getHits()) {
			_hits.add(new SearchHitImpl(hit));
		}
	}

	@Override
	public List<ISearchHit> getHits() {
		return Collections.unmodifiableList(_hits);
	}

	@Override
	public long getTookInMillis() {
		return _response.getTook().getMillis();
	}

	@Override
	public long getTotalHitsCount() {
		return _response.getHits().getTotalHits().value;
	}

}
