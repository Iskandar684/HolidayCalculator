package ru.iskandar.holiday.calculator.service.event;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ejb.Singleton;

import lombok.NonNull;

/**
 * Поставщик отправителей событий.
 *
 */
@Singleton
public class EventSendersProvider {

	/** Отправители событий */
	private final static List<IHolidayCalculatorEventSender> _senders = new CopyOnWriteArrayList<>();

	/**
	 * Возвращает список отправителей событий.
	 *
	 * @return отправителей событий
	 */
	public List<IHolidayCalculatorEventSender> getSenders() {
		return Collections.unmodifiableList(_senders);
	}

	/**
	 * Регистрирует отправителя событий.
	 *
	 * @param aSender отправитель событий
	 *
	 */
	// TODO Реализовать регистрацию через точку расширения.
	public static void register(@NonNull IHolidayCalculatorEventSender aSender) {
		_senders.add(aSender);
	}

}
