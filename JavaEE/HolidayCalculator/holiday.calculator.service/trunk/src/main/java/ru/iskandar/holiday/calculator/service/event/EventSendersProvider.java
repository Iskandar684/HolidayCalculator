package ru.iskandar.holiday.calculator.service.event;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.service.event.sse.SSEHolidayCalculatorEventSender;

/**
 * Поставщик отправителей событий.
 *
 */
@Stateless
public class EventSendersProvider {

	/** Отправить событий Server Sent Events (SSE) */
	@EJB
	private SSEHolidayCalculatorEventSender _sseSender;

	/**
	 * Возвращает список отправителей событий.
	 *
	 * @return отправителей событий
	 */
	public List<IHolidayCalculatorEventSender> getSenders() {
		List<IHolidayCalculatorEventSender> senders = new ArrayList<>();
		senders.add(_sseSender);
		return senders;
	}

}
