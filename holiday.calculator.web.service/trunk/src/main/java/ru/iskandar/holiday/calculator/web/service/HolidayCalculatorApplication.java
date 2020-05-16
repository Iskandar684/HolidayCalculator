package ru.iskandar.holiday.calculator.web.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.logging.Logger;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@ApplicationPath("/holiday-calculator-web-service")
public class HolidayCalculatorApplication extends Application {

    private static final Logger LOG = Logger.getLogger(HolidayCalculatorApplication.class);

    public HolidayCalculatorApplication() {
        super();
        LOG.info("start configure EchoApplication");
        OpenAPI oas = new OpenAPI();
        Info info = new Info().title("Методы веб-сервиса учета отгулов.");
        oas.info(info);
        SwaggerConfiguration oasConfig =
                new SwaggerConfiguration().openAPI(oas).prettyPrint(true).resourcePackages(
                        Collections.singleton("ru.iskandar.holiday.calculator.web.service"));
        try {
            OpenApiContext ctx = new JaxrsOpenApiContextBuilder().application(this)
                    .openApiConfiguration(oasConfig).buildContext(true);
            LOG.info("OpenApiContext " + ctx);
        } catch (OpenApiConfigurationException e) {
            LOG.error("Ошибка настройки swagger.", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(HolidayCalculatorWebService.class);
        set.add(HolidayCalculatorApplication.class);
        set.add(io.swagger.v3.jaxrs2.integration.resources.OpenApiResource.class);
        set.add(io.swagger.v3.oas.integration.GenericOpenApiScanner.class);
        return set;
    }
}
