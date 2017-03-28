package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote;
import ru.iskandar.holiday.calculator.service.ejb.IPermissionsService;
import ru.iskandar.holiday.calculator.service.ejb.InvalidStatementException;
import ru.iskandar.holiday.calculator.service.ejb.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.ejb.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.ejb.StatementNotFoundException;

/**
 * Модель учета отгулов
 *
 */
public class HolidayCalculatorModel implements Serializable {
	/**
	 * Индентификатор для сериализации
	 */
	private static final long serialVersionUID = -2279698461127019581L;
	/** Текущий пользователь */
	private final User _currenUser;

	private IHolidayCalculatorModelPermissions _permissions;

	private IHolidayCalculatorRemote _service;

	private transient CopyOnWriteArrayList<IHolidayCalculatorModelListener> _listeners;

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
		return new TakeHolidayStatementBuilder(this);
	}

	/**
	 * Подает заявление
	 *
	 * @param aStatement
	 *            заявление
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 */
	void sendHolidayStatement(HolidayStatement aStatement) throws StatementAlreadySendedException {
		_service.sendStatement(aStatement);
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
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {
				try {
					new ServerEventsSubscriber().subscribe(aInitialContext,
							new HolidayCalculatorEventListener(HolidayCalculatorModel.this));
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
		try {
			_service = (IHolidayCalculatorRemote) aInitialContext.lookup(IHolidayCalculatorRemote.JNDI_NAME);
		} catch (NamingException e) {
			throw new HolidayCalculatorModelInitException("Ошибка получения сервиса учета отгулов", e);
		}
	}

	public boolean canConsider() {
		return _permissions.canConsider();
	}

	public int getUnConsideredStatementsCount() {
		if (!canConsider()) {
			throw new PermissionDeniedException("Нет прав на получение количества нерассмотренных заявлений");
		}
		Set<Statement> statements = _service.loadStatements(EnumSet.of(StatementStatus.NOT_CONSIDERED));
		return statements.size();
	}

	/**
	 * Одобряет заявление
	 *
	 * @param aStatement
	 *            заявление
	 * @return заявление
	 * @throws StatementAlreadyConsideredException
	 *             если заявление уже было рассмотрено
	 * @throws NullPointerException
	 *             если aStatement {@code null}
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 * @throws StatementNotFoundException
	 *             если заявление с указанным UUID не найдено
	 */
	public void approve(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		_service.approve(aStatement);
	}

	/**
	 * Отклоняет заявление
	 *
	 * @param aStatement
	 *            заявление
	 * @return заявление
	 * @throws StatementAlreadyConsideredException
	 *             если заявление уже было рассмотрено
	 * @throws NullPointerException
	 *             если aStatement {@code null}
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 * @throws StatementNotFoundException
	 *             если заявление с указанным UUID не найдено
	 */
	public void reject(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		_service.reject(aStatement);
	}

	public void addListener(IHolidayCalculatorModelListener aListener) {
		getListeners().add(aListener);
	}

	public void removeListener(IHolidayCalculatorModelListener aListener) {
		getListeners().remove(aListener);
	}

	/**
	 * @return the listeners
	 */
	private CopyOnWriteArrayList<IHolidayCalculatorModelListener> getListeners() {
		if (_listeners == null) {
			_listeners = new CopyOnWriteArrayList<>();
		}
		return _listeners;
	}

	/**
	 * Оповещает слушателей
	 * 
	 * @param aEvent
	 *            событие
	 */
	void fireEvent(HolidayCalculatorEvent aEvent) {
		for (IHolidayCalculatorModelListener listener : getListeners()) {
			listener.handleEvent(aEvent);
		}
	}

	/**
	 * Возвращает общее количество отгулов
	 *
	 * @return количество отгулов
	 */
	public int getHolidaysQuantity() {
		return _service.getHolidaysQuantity(_currenUser);
	}

	/**
	 * Возвращает количество исходящих дней отгула. Это количество дней, на
	 * которое уменьшется количество общее дней отгула, после того как заявление
	 * на отгул будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отгула
	 */
	public int getOutgoingHolidaysQuantity() {
		return 1;
	}

	/**
	 * Возвращает количество приходящих отгулов. Это количество дней, на которое
	 * будет увеличино общее количество отгулов, после того как засчитают отзыв.
	 *
	 * @return количество приходящих отгулов
	 */
	public int getIncomingHolidaysQuantity() {
		return 7;
	}

	public Collection<Statement> getIncomingStatements() {
		return _service.getIncomingStatements();
	}

}
