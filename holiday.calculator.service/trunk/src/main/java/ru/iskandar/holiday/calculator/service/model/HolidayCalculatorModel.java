package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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

	/** Поставщик сервисов */
	private IServicesProvider _servicesProvider;

	/** Слушатели */
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
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	void sendHolidayStatement(HolidayStatement aStatement) throws StatementAlreadySendedException {
		_servicesProvider.getHolidayCalculatorService().sendStatement(aStatement);
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
		_servicesProvider = new RemoteServicesProvider(aInitialContext);
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
	}

	/**
	 * Возвращает наличие полномочий на рассмотрение заявлений
	 *
	 * @return {@code true}, если текущий пользователь имеет полномочие
	 *         рассматривать заявления
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public boolean canConsider() {
		return _servicesProvider.getHolidayCalculatorService().canConsider();
	}

	/**
	 * @return
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getUnConsideredStatementsCount() {
		if (!canConsider()) {
			throw new PermissionDeniedException("Нет прав на получение количества нерассмотренных заявлений");
		}
		Set<Statement> statements = _servicesProvider.getHolidayCalculatorService()
				.loadStatements(EnumSet.of(StatementStatus.NOT_CONSIDERED));
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
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public void approve(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		_servicesProvider.getHolidayCalculatorService().approve(aStatement);
	}

	/**
	 * @param aStatement
	 * @return
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public boolean canApprove(Statement aStatement) {
		Objects.requireNonNull(aStatement);
		if (!canConsider()) {
			return false;
		}
		StatementStatus status = aStatement.getStatus();
		return StatementStatus.NOT_CONSIDERED.equals(status);
	}

	/**
	 * @param aStatement
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 * @return
	 */
	public boolean canReject(Statement aStatement) {
		Objects.requireNonNull(aStatement);
		if (!canConsider()) {
			return false;
		}
		StatementStatus status = aStatement.getStatus();
		return StatementStatus.NOT_CONSIDERED.equals(status);
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
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public void reject(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		_servicesProvider.getHolidayCalculatorService().reject(aStatement);
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
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getHolidaysQuantity() {
		return _servicesProvider.getHolidayCalculatorService().getHolidaysQuantity(_currenUser);
	}

	/**
	 * Возвращает количество исходящих дней отгула. Это количество дней, на
	 * которое уменьшется количество общее дней отгула, после того как заявление
	 * на отгул будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отгула
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getOutgoingHolidaysQuantity() {
		return _servicesProvider.getHolidayCalculatorService().getOutgoingHolidaysQuantity(_currenUser);
	}

	/**
	 * Возвращает количество приходящих отгулов. Это количество дней, на которое
	 * будет увеличино общее количество отгулов, после того как засчитают отзыв.
	 *
	 * @return количество приходящих отгулов
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getIncomingHolidaysQuantity() {
		return _servicesProvider.getHolidayCalculatorService().getIncomingHolidaysQuantity(_currenUser);
	}

	/**
	 * @return
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public Collection<Statement> getIncomingStatements() {
		return _servicesProvider.getHolidayCalculatorService().getIncomingStatements();
	}

	/**
	 * Возвращает количество не использованных дней отпуска в этом периоде
	 *
	 * @return количество дней
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getLeaveCount() {
		// TODO
		return 28;
	}

	/**
	 * Возвращает количество исходящих дней отпуска. Это количество дней, на
	 * которое уменьшется количество дней отпуска в этом периоде, после того как
	 * заявление на отпуск будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отпуска.
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getOutgoingLeaveCount() {
		// TODO
		return 14;
	}

	/**
	 * Возвращает дату начала следующего периода
	 *
	 * @return дата начала следующего периода
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public Date getNextLeaveStartDate() {
		// TODO
		return new Date();
	}

}
