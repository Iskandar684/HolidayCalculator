package ru.iskandar.holiday.calculator.service.event.sse;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;
import ru.iskandar.holiday.calculator.service.event.IHolidayCalculatorEventSender;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 *
 */
@Singleton
@LocalBean
@Path("/")
public class SSEHolidayCalculatorEventSender implements IHolidayCalculatorEventSender {

	@Context
	private Sse _sse;

	private volatile SseBroadcaster _sseBroadcaster;

	private ObjectMapper _objectMapper;

	@PostConstruct
	public void init() {
		_objectMapper = new ObjectMapper();
		_objectMapper.registerModule(new JavaTimeModule());
		_objectMapper.registerModule(new JaxbAnnotationModule());
		_objectMapper.setConfig(
				_objectMapper.getSerializationConfig().with(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT)));
		_sseBroadcaster = _sse.newBroadcaster();
	}

	@Override
	public void send(HolidayCalculatorEvent aEvent) throws HolidayCalculatorException {
		String eventData;
		try {
			eventData = _objectMapper.writeValueAsString(aEvent);
		} catch (JsonProcessingException e) {
			throw new HolidayCalculatorException(e.getMessage(), e);
		}
		OutboundSseEvent event = _sse.newEventBuilder()//
				.mediaType(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name()))
				.id(aEvent.getId())//
				.data(eventData)//
				.reconnectDelay(10000)//
				.build();
		_sseBroadcaster.broadcast(event);

	}

	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Path("/subscribeToAllEvents")
	public void subscribe(@Context SseEventSink eventSink, @Context Sse sse) {
		_sseBroadcaster.register(eventSink);
	}

}
