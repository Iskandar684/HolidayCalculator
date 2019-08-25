package ru.iskandar.holiday.calculator.service.model.search;

import java.util.Map;

import org.elasticsearch.search.SearchHit;

class SearchHitImpl implements ISearchHit {

	/**
	 *
	 */
	private static final long serialVersionUID = -1268252145152327295L;

	private final Map<String, Object> _sourceAsMap;

	SearchHitImpl(SearchHit aSearchHit) {
		_sourceAsMap = aSearchHit.getSourceAsMap();
	}

	@Override
	public Map<String, Object> getSourceAsMap() {
		return _sourceAsMap;
	}

}
