package ru.iskandar.holiday.calculator.dataconnection;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import ru.iskandar.holiday.calculator.dataconnection.internal.SearchResultFactory;

public class SearchConnector {

	private final SearchResultFactory _resultFactory = new SearchResultFactory();

	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("Не указана строка поиска.");
		}
		String text = args[0];
		SearchConnector connection = new SearchConnector();
		try {
			connection.search(text);
		} catch (SearchException e) {
			e.printStackTrace();
		}
	}

	private RestHighLevelClient createClient() {
		HttpHost host = new HttpHost("localhost", 9200, "http");
		RestClientBuilder restClient = RestClient.builder(host);
		return new RestHighLevelClient(restClient);
	}

	public ISearchResult search(String aSearchText) throws SearchException {
		RestHighLevelClient client = createClient();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("docContent", aSearchText));
		searchRequest.source(searchSourceBuilder);
		SearchResponse response;
		try {
			response = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new SearchException(String.format("Ошибка поиска. [searchText=%s].", aSearchText), e);
		}
		try {
			client.close();
		} catch (IOException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, getClass().getName(), "Ошибка закрытия подключения к elasticsearch.", e));
		}
		return _resultFactory.createSearchResult(response, aSearchText);
	}

}
