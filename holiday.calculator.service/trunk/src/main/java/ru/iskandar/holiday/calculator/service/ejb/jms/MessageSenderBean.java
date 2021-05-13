package ru.iskandar.holiday.calculator.service.ejb.jms;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Objects;
import static ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorJMSConstants.DESTINATION_ID;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
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

import lombok.extern.jbosslog.JBossLog;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Сервис отправки сообщений
 */
@Stateless
@Path("/")
@JBossLog
public class MessageSenderBean {

    /** Контекст JMS */
    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
    private JMSContext _context;

    /** Очередь */
    @Resource(name = DESTINATION_ID)
    private Destination _destination;

    @Context
    private Sse _sse;

    private volatile SseBroadcaster _sseBroadcaster;

    private ObjectMapper _objectMapper;

    @PostConstruct
    public void init() {
        _objectMapper = new ObjectMapper();
        _objectMapper.registerModule(new JavaTimeModule());
        _objectMapper.registerModule(new JaxbAnnotationModule());
        _objectMapper.setConfig(_objectMapper.getSerializationConfig()
                .with(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT)));
        _sseBroadcaster = _sse.newBroadcaster();
    }

    /**
     * Отправляет сообщение
     *
     * @param aEvent сообщение
     * @throws JMSException ошибка отправки сообщения
     */
    public void send(HolidayCalculatorEvent aEvent) throws JMSException {
        Objects.requireNonNull(aEvent, "Не указано сообщение");
        if (_destination == null) {
            throw new IllegalStateException(
                    String.format("Отсутствует очередь; destinationId='%s'", DESTINATION_ID));
        }

        ObjectMessage om = _context.createObjectMessage();
        om.setObject(aEvent);

        JMSProducer producer = _context.createProducer();

        producer.send(_destination, om);

        try {
            sendToWebClient(aEvent);
        } catch (Exception e) {
            log.error("Ошибка оповещения веб-клиента.", e);
        }
    }

    private void sendToWebClient(HolidayCalculatorEvent aEvent) throws JsonProcessingException {

        String eventData = _objectMapper.writeValueAsString(aEvent);
        OutboundSseEvent event = _sse.newEventBuilder()//
                .mediaType(
                        MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name()))
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
