package ru.iskandar.holiday.calculator.web.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
public class HolidayCalculatorResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext aRequestContext, ContainerResponseContext aResponse)
            throws IOException {
        final MediaType type = aResponse.getMediaType();
        if ((type != null) && !type.getParameters().containsKey(MediaType.CHARSET_PARAMETER)) {
            MediaType typeWithCharset = type.withCharset(StandardCharsets.UTF_8.name());
            aResponse.getHeaders().putSingle(HttpHeaders.CONTENT_TYPE, typeWithCharset);
        }
    }

}
