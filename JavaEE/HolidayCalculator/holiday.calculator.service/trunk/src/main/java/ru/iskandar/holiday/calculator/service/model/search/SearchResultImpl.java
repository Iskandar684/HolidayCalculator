package ru.iskandar.holiday.calculator.service.model.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

class SearchResultImpl implements ISearchResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -2137785944615024704L;

	/** Совпадения */
	private final List<ISearchHit> _hits = new ArrayList<>();

	/** Строка поиска */
	private final String _searchString;

	private final long _tookInMillis;

	private final long _totalHitsCount;

	SearchResultImpl(SearchResponse aResponse, String aSearchString) {
		_searchString = Objects.requireNonNull(aSearchString);
		for (SearchHit hit : aResponse.getHits()) {
			_hits.add(new SearchHitImpl(hit));
		}
		_tookInMillis = aResponse.getTook().getMillis();
		_totalHitsCount = aResponse.getHits().getTotalHits().value;
	}

	@Override
	public List<ISearchHit> getHits() {
		return Collections.unmodifiableList(_hits);
	}

	@Override
	public long getTookInMillis() {
		return _tookInMillis;
	}

	@Override
	public long getTotalHitsCount() {
		return _totalHitsCount;
	}

	@Override
	public String getSearchString() {
		return _searchString;
	}

}
