package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Модель учета отгулов
 *
 */
public class HolidayCalculatorModel implements Serializable {
	/** Текущий пользователь */
	private final User _currenUser;

	/**
	 * Индентификатор для сериализации
	 */
	private static final long serialVersionUID = -2279698461127019581L;

	/**
	 * Конструктор
	 */
	HolidayCalculatorModel(User aCurrentUser) {
		Objects.requireNonNull(aCurrentUser);
		_currenUser = aCurrentUser;
	}

	/**
	 * Возвращает текущего пользователя
	 *
	 * @return текущий пользователь
	 *
	 */
	public User getCurrentUser() {
		return _currenUser;
	}

	/**
	 * Создает формирователь заявления на отгул
	 *
	 * @return формирователь заявления на отгул
	 */
	public TakeHolidayStatementBuilder createHolidayStatementBuilder() {
		if (!canCreateHolidayStatementBuilder()) {
			throw new IllegalStateException("Формирование заявления на отгул запрещено");
		}
		return new TakeHolidayStatementBuilder();
	}

	/**
	 * Возвращает возможность формирования заявления на отгул
	 *
	 * @return возможность формирования заявления на отгул
	 */
	public boolean canCreateHolidayStatementBuilder() {
		return true;
	}

	public synchronized void init(final InitialContext aInitialContext) throws HolidayCalculatorModelInitException {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				try {
					new ServerEventsSubscriber().subscribe(aInitialContext, new HolidayCalculatorEventListener());
				} catch (NamingException | JMSException e) {
					// TODO логировать в клиентский логгер
					e.printStackTrace();
				}

			}

		};
		Executors.newSingleThreadExecutor().submit(run);

	}

}
