package ru.iskandar.holiday.calculator.web.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.Reader;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

/**
 * Тесты генерации openApi-файлов веб-сервиса.
 */
public class GenerateOpenAPITest {

    @Test
    public void testGenerateOpenApiJSON() {
        Reader reader = new Reader(new SwaggerConfiguration());
        OpenAPI openAPI = reader.read(HolidayCalculatorWebService.class);
        String res = Json.pretty(openAPI);
        assertNotNull(res);
    }

    @Test
    public void testGenerateOpenApiYAML() {
        Reader reader = new Reader(new SwaggerConfiguration());
        OpenAPI openAPI = reader.read(HolidayCalculatorWebService.class);
        String res = Yaml.pretty(openAPI);
        assertNotNull(res);
    }

}
