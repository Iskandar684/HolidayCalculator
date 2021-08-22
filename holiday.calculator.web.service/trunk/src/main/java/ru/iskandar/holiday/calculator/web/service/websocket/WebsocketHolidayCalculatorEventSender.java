package ru.iskandar.holiday.calculator.web.service.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.jbosslog.JBossLog;
import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;
import ru.iskandar.holiday.calculator.service.event.EventSendersProvider;
import ru.iskandar.holiday.calculator.service.event.IHolidayCalculatorEventSender;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;


@JBossLog
@ServerEndpoint(value = "/websocket", encoders = MessageEncoder.class)
// Важно, чтобы этот класс бы war-нике. Иначе, не веб-сокет не находит.
public class WebsocketHolidayCalculatorEventSender implements IHolidayCalculatorEventSender {

	private final List<Session> _sessions = new ArrayList<>();

	@PostConstruct
	public void init() {
		EventSendersProvider.register(this);
	}

//	@OnMessage
//	public String sayHello(String name) {
//		// обработка не требуется Метод вызывает клиент
//		return name;
//	}

	@OnOpen
	public void helloOnOpen(Session session) {
		log.info("websocket on open " + session);
		_sessions.add(session);
	}

	@OnClose
	public void helloOnClose(CloseReason reason) {
		log.info("websocket on Close " + reason.getCloseCode());
		List<Session> closedSessions = _sessions.stream().filter(session -> !session.isOpen())
				.collect(Collectors.toList());
		_sessions.removeAll(closedSessions);
	}

	@Override
	public void send(HolidayCalculatorEvent aEvent) throws HolidayCalculatorException {
		for (Session session : _sessions) {
			try {
				session.getBasicRemote().sendObject(aEvent);
			} catch (IOException | EncodeException e) {
				throw new HolidayCalculatorException(e.getMessage(), e);
			}
		}

	}

}
