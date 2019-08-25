package ru.iskandar.holiday.calculator.service.model.search;

import java.io.Serializable;
import java.util.Map;

public interface ISearchHit extends Serializable {

	Map<String, Object> getSourceAsMap();

}
