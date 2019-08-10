package ru.iskandar.holiday.calculator.dataconnection.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ru.iskandar.holiday.calculator.dataconnection.ISearchHit;
import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;

class SearchResultImpl implements ISearchResult {

	private final List<ISearchHit> _hits = new ArrayList<>();

	SearchResultImpl(List<ISearchHit> aHits) {
		Objects.requireNonNull(aHits);
		_hits.addAll(aHits);
	}

	@Override
	public List<ISearchHit> getHits() {
		return Collections.unmodifiableList(_hits);
	}

}
