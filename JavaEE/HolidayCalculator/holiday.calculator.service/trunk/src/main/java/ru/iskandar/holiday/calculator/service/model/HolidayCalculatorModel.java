package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.iskandar.holiday.calculator.service.ejb.IPermissionsService;

/**
 * Модель учета отгулов
 *
 */
public class HolidayCalculatorModel implements Serializable {

	/** Текущий пользователь */
	private final User _currenUser;

	private IHolidayCalculatorModelPermissions _permissions;
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

		IPermissionsService permService;
		try {
			permService = (IPermissionsService) aInitialContext.lookup(IPermissionsService.JNDI_NAME);
		} catch (NamingException e) {
			throw new HolidayCalculatorModelInitException("Ошибка получения сервиса работы с полномочиями", e);
		}
		_permissions = new CurrentUserHolidayCalculatorModelPermissions(permService);
	}

	public boolean canConsider() {
		return _permissions.canConsider();
	}

}
