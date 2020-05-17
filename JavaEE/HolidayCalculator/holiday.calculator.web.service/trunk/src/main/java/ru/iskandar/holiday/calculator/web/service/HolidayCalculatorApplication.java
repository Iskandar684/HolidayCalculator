package ru.iskandar.holiday.calculator.web.service;

import java.util.Collections;
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
        Info info = new Info().title("Методы веб-сервиса учета отгулов.");
        oas.info(info);
        SwaggerConfiguration oasConfig =
                new SwaggerConfiguration().openAPI(oas).prettyPrint(true).resourcePackages(
                        Collections.singleton("ru.iskandar.holiday.calculator.web.service"));
        try {
            new JaxrsOpenApiContextBuilder<>().application(this).openApiConfiguration(oasConfig)
                    .buildContext(true);
            log.info("Swagger успешно настроен.");
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
        return set;
    }
}
