package ru.iskandar.holiday.calculator.web.service;

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
import ru.iskandar.holiday.calculator.service.ejb.IUserServiceRemote;
import ru.iskandar.holiday.calculator.web.service.sse.SSEHolidayCalculatorEventSender;

/**
 * Приложение веб-сервиса учета отгулов.
 */
@ApplicationPath("/holiday-calculator-web-service")
@JBossLog
public class HolidayCalculatorApplication extends Application {

	/**
	 * Конструктор.
	 */
	public HolidayCalculatorApplication() {
		super();
		OpenAPI oas = new OpenAPI();
		Info info = new Info().title("Веб-сервис учета отгулов.");
		oas.info(info);
		Set<String> resourceClasses = new HashSet<>();
		resourceClasses.add(HolidayCalculatorWebService.class.getName());
		resourceClasses.add(IUserServiceRemote.class.getName());
		resourceClasses.add(SSEHolidayCalculatorEventSender.class.getName());
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
		set.add(HolidayCalculatorWebService.class);
		set.add(io.swagger.v3.jaxrs2.integration.resources.OpenApiResource.class);
		set.add(SSEHolidayCalculatorEventSender.class);
		return set;
	}

	// @Override
	// public Set<Object> getSingletons() {
	// try {
	// return
	// Collections.singleton(InitialContext.doLookup(IUserServiceRemote.JNDI_NAME));
	// } catch (NamingException e) {
	// log.error(e.getMessage(), e);
	// return Collections.emptySet();
	// }
	// }
}
