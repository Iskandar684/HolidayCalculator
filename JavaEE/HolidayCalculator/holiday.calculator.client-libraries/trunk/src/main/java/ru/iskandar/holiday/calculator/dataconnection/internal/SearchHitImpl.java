package ru.iskandar.holiday.calculator.dataconnection.internal;

import java.util.Map;
import java.util.Objects;

import org.elasticsearch.search.SearchHit;

import ru.iskandar.holiday.calculator.dataconnection.ISearchHit;

class SearchHitImpl implements ISearchHit {

	private final SearchHit _hit;

	SearchHitImpl(SearchHit aSearchHit) {
		_hit = Objects.requireNonNull(aSearchHit);
	}

	@Override
	public Map<String, Object> getSourceAsMap() {
		return _hit.getSourceAsMap();
	}

}
