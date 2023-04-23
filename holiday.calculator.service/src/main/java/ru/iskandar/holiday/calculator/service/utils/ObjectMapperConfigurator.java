package ru.iskandar.holiday.calculator.service.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import lombok.experimental.UtilityClass;

/**
 * @author iskandar
 *
 */
@UtilityClass
public class ObjectMapperConfigurator {

	public ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new JaxbAnnotationModule());
		objectMapper.setConfig(
				objectMapper.getSerializationConfig().with(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT)));
		return objectMapper;
	}

}
