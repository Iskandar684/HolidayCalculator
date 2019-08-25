package ru.iskandar.holiday.calculator.service.ejb.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Служба поиска.
 */
@Stateless
@Local(ISearchServiceLocal.class)
public class SearchBean implements ISearchServiceLocal {

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
			IndexRequest indexRequest = new IndexRequest("user_index").id(id).source(fields);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			System.out.println("indexResponse " + indexResponse);
		} catch (IOException e) {
			throw new SearchServiceException(
					String.format("Ошибка добавления/обновления пользователя %s в elasticsearch.", aUser), e);
		}
	}

}
