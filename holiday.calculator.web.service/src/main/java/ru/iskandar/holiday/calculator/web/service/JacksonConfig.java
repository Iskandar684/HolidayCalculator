package ru.iskandar.holiday.calculator.web.service;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper _objectMapper;

    public JacksonConfig() {
        _objectMapper = new ObjectMapper();
        _objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        _objectMapper.registerModule(new JavaTimeModule());
        _objectMapper.setDateFormat(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT));
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return _objectMapper;
    }
}