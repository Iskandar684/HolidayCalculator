package ru.iskandar.holiday.calculator.dataconnection;

import java.io.IOException;
import java.util.Map;

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
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchConnector {

	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("Не указана строка поиска.");
		}
		String text = args[0];
		SearchConnector connection = new SearchConnector();
		connection.search(text);
	}

	private RestHighLevelClient createClient() {
		HttpHost host = new HttpHost("localhost", 9200, "http");
		RestClientBuilder restClient = RestClient.builder(host);
		return new RestHighLevelClient(restClient);
	}

	public void search(String aSearchText) {
		// FIXME вернуть результат
		RestHighLevelClient client = createClient();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("docContent", aSearchText));
		// searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		RequestOptions options = RequestOptions.DEFAULT;
		try {
			SearchResponse response = client.search(searchRequest, options);
			SearchHits hits = response.getHits();
			System.out.println("hits size " + hits.getHits().length);
			for (SearchHit hit : hits) {
				System.out.println("hit " + hit);
				Map<String, DocumentField> fields = hit.getFields();
				System.out.println("fields " + fields);
				Map<String, Object> source = hit.getSourceAsMap();
				System.out.println("source " + source);
				System.out.println("firstName " + source.get("firstName"));
			}

			System.out.println("response " + response);
		} catch (IOException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, getClass().getName(), e.getMessage(), e));
		}

		try {
			client.close();
		} catch (IOException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, getClass().getName(), "Ошибка закрытия подключения к elasticsearch.", e));
		}
	}

	// private void addToIndex(RestHighLevelClient client) {
	// Map<String, Object> jsonMap = new HashMap<>();
	// jsonMap.put("firstName", "Ilmir");
	// jsonMap.put("docContent", "например");
	// jsonMap.put("postDate", new Date());
	// IndexRequest indexRequest = new
	// IndexRequest("test_index").id("10").source(jsonMap);
	// IndexResponse indexResponse;
	// try {
	// indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	// System.out.println("indexResponse " + indexResponse);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
