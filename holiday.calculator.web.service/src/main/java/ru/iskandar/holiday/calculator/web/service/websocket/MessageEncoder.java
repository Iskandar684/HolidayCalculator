package ru.iskandar.holiday.calculator.web.service.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.utils.ObjectMapperConfigurator;

public class MessageEncoder implements Encoder.Text<HolidayCalculatorEvent> {

	private ObjectMapper _objectMapper;

	public MessageEncoder() {
		_objectMapper = ObjectMapperConfigurator.createObjectMapper();

	}

	@Override
	public String encode(HolidayCalculatorEvent aMessage) throws EncodeException {
		String eventData;
		try {
			eventData = _objectMapper.writeValueAsString(aMessage);
		} catch (JsonProcessingException e) {
			throw new EncodeException(aMessage, e.getMessage(), e);
		}
		System.out.println("eventData  " + eventData);
		return eventData;
	}

	@Override
	public void destroy() {
		// действие не требуется
	}

	@Override
	public void init(EndpointConfig arg0) {
		// действие не требуется
	}

}
