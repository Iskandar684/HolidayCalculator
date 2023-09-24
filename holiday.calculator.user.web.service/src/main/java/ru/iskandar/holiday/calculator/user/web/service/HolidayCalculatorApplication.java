package ru.iskandar.holiday.calculator.user.web.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.jbosslog.JBossLog;
import ru.iskandar.holiday.calculator.user.service.ejb.IUserServiceRemote;

/**
 * Приложение веб-сервиса учета отгулов.
 */
@ApplicationPath("/holiday-calculator-user-web-service")
@JBossLog
public class HolidayCalculatorApplication extends Application {

	/**
	 * Конструктор.
	 */
	public HolidayCalculatorApplication() {
		super();
		OpenAPI oas = new OpenAPI();
		Info info = new Info().title("Веб-сервис пользователей учета отгулов.");
		oas.info(info);
		Set<String> resourceClasses = new HashSet<>();
		resourceClasses.add(IUserServiceRemote.class.getName());
		SwaggerConfiguration oasConfig = new SwaggerConfiguration().openAPI(oas).prettyPrint(true)
				.resourceClasses(resourceClasses);
		try {
			new JaxrsOpenApiContextBuilder<>().application(this).openApiConfiguration(oasConfig).buildContext(true);
			log.info("Swagger (open API) успешно настроен.");
		} catch (OpenApiConfigurationException e) {
			log.error("Ошибка настройки swagger.", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(io.swagger.v3.jaxrs2.integration.resources.OpenApiResource.class);
		return set;
	}

}
