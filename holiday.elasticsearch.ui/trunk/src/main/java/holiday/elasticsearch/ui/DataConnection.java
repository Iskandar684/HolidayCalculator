package holiday.elasticsearch.ui;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class DataConnection {

	public void connect() {
		System.out.println("connect ");
		HttpHost host = new HttpHost("localhost", 9200, "http");
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
		System.out.println("connect client " + client);

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder
				.query(QueryBuilders.matchQuery("docContent", "например характер Восприятие в широком значении"));
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
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			client.close();
			System.out.println("close");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataConnection connection = new DataConnection();
		connection.connect();
	}

}
