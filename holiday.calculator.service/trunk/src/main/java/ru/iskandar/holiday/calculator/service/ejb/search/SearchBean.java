package ru.iskandar.holiday.calculator.service.ejb.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import ru.iskandar.holiday.calculator.service.model.search.ISearchResult;
import ru.iskandar.holiday.calculator.service.model.search.SearchConstants;
import ru.iskandar.holiday.calculator.service.model.search.SearchResultFactory;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Служба поиска.
 */
@Stateless
@Local(ISearchServiceLocal.class)
public class SearchBean implements ISearchServiceLocal {

	/** Фабрика результатов поиска */
	private final SearchResultFactory _resultFactory = new SearchResultFactory();

	private RestHighLevelClient createClient() {
		HttpHost host = new HttpHost("localhost", 9200, "http");
		return new RestHighLevelClient(RestClient.builder(host));
	}

	@Override
	public void addOrUpdate(User aUser) throws SearchServiceException {
		try (RestHighLevelClient client = createClient()) {
			Map<String, Object> fields = new HashMap<>();
			fields.put("firstName", aUser.getFirstName());
			fields.put("lastName", aUser.getLastName());
			fields.put("patronymic", aUser.getPatronymic());
			// fields.put("employmentDate", aUser.getEmploymentDate());
			fields.put("note", aUser.getNote());
			String id = aUser.getId().getUUID().toString();
			fields.put(SearchConstants.USER_ID_KEY, id);
			IndexRequest indexRequest = new IndexRequest("user_index").id(id).source(fields);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			System.out.println("indexResponse " + indexResponse);
		} catch (IOException e) {
			throw new SearchServiceException(
					String.format("Ошибка добавления/обновления пользователя %s в elasticsearch.", aUser), e);
		}
	}

	@Override
	public ISearchResult search(String aSearchText) throws SearchServiceException {
		RestHighLevelClient client = createClient();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("note", aSearchText));
		searchRequest.source(searchSourceBuilder);
		SearchResponse response;
		try {
			response = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new SearchServiceException(String.format("Ошибка поиска. [searchText=%s].", aSearchText), e);
		}
		return _resultFactory.createSearchResult(response, aSearchText);
	}

}
