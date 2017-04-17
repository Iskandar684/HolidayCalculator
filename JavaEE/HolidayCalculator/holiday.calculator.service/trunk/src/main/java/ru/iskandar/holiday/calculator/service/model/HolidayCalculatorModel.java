package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import javax.ejb.EJBAccessException;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Модель учета отгулов
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

	/** Идентификатор клиента */
	private ClientId _clientId;

	private transient TakeHolidayStatementBuilder _takeHolidayStatementBuilder;

	private transient TakeLeaveStatementBuilder _takeLeaveStatementBuilder;

	private transient MakeRecallStatementBuilder _makeRecallStatementBuilder;

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
	 * Возвращает формирователь заявления на отгул
	 *
	 * @return формирователь заявления на отгул
	 */
	public TakeHolidayStatementBuilder getHolidayStatementBuilder() {
		if (!canCreateHolidayStatement()) {
			throw new IllegalStateException("Формирование заявления на отгул запрещено");
		}
		if (_takeHolidayStatementBuilder == null) {
			_takeHolidayStatementBuilder = new TakeHolidayStatementBuilder(this);
		}
		return _takeHolidayStatementBuilder;
	}

	public TakeLeaveStatementBuilder getLeaveStatementBuilder() {
		if (_takeLeaveStatementBuilder == null) {
			_takeLeaveStatementBuilder = new TakeLeaveStatementBuilder(this);
		}
		return _takeLeaveStatementBuilder;
	}

	LeaveStatement getLastLeaveStatement() {
		IHolidayCalculatorService holidayCalculatorService = _servicesProvider.getHolidayCalculatorService();
		LeaveStatement leaveStatement = null;
		for (Statement statement : holidayCalculatorService.getAllStatements(_currenUser)) {
			if (StatementType.LEAVE_STATEMENT.equals(statement.getStatementType())) {
				if (leaveStatement == null) {
					leaveStatement = (LeaveStatement) statement;
				}
				if ((leaveStatement != null) && leaveStatement.getCreateDate().before(statement.getCreateDate())) {
					leaveStatement = (LeaveStatement) statement;
				}
			}
		}
		return leaveStatement;
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
		HolidayStatement statement = _servicesProvider.getHolidayCalculatorService().sendStatement(aStatement);
		StatementSendedEvent event = new StatementSendedEvent(statement);
		event.setInitiator(createCurrentEventInitiator());
		fireEvent(event);
	}

	private EventInitiator createCurrentEventInitiator() {
		return EventInitiator.create(_clientId, _currenUser);
	}

	/**
	 * Подает заявление на отпуск
	 *
	 * @param aStatement
	 *            заявление на отпуск
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	void sendLeaveStatement(LeaveStatement aStatement) throws StatementAlreadySendedException {
		LeaveStatement statement = _servicesProvider.getHolidayCalculatorService().sendStatement(aStatement);
		StatementSendedEvent event = new StatementSendedEvent(statement);
		event.setInitiator(createCurrentEventInitiator());
		fireEvent(event);
	}

	public MakeRecallStatementBuilder getRecallStatementBuilder() {
		if (_makeRecallStatementBuilder == null) {
			_makeRecallStatementBuilder = new MakeRecallStatementBuilder(this);
		}
		return _makeRecallStatementBuilder;
	}

	public boolean canCreateMakeRecallStatement() {
		LeaveStatement statement = getLastLeaveStatement();
		return statement != null;
	}

	public boolean canCreateLeaveStatement() {
		// TODO
		return true;
	}

	/**
	 * Подает заявление на отзыв
	 *
	 * @param aStatement
	 *            заявление на отзыв
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	void sendRecallStatement(RecallStatement aStatement) throws StatementAlreadySendedException {
		RecallStatement statement = _servicesProvider.getHolidayCalculatorService().sendStatement(aStatement);
		StatementSendedEvent event = new StatementSendedEvent(statement);
		event.setInitiator(createCurrentEventInitiator());
		fireEvent(event);
	}

	/**
	 * Возвращает возможность формирования заявления на отгул
	 *
	 * @return возможность формирования заявления на отгул
	 */
	public boolean canCreateHolidayStatement() {
		return true;
	}

	/**
	 * Инициализирует модель
	 *
	 * @param aInitialContext
	 *            контекст
	 * @param aLogger
	 *            логгер
	 * @param aClientId
	 *            идентификатор клиента
	 * @throws HolidayCalculatorModelInitException
	 *             если произошла ошибка при инициализации
	 * @throws NullPointerException
	 *             если aInitialContext или aLogger или aClientId равны
	 *             {@code null}
	 */
	public synchronized void init(final InitialContext aInitialContext, final IHolidayCalculatorModelLogger aLogger,
			ClientId aClientId) throws HolidayCalculatorModelInitException {
		Objects.requireNonNull(aLogger, "Не указан логгер");
		Objects.requireNonNull(aInitialContext, "Не указан контекст");
		Objects.requireNonNull(aClientId, "Не указан идентификатор клиента");
		_clientId = aClientId;
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
					aLogger.logError("Ошибка подписки на серверные события", e);
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
	 * Возвращает количество нерассмотренных заявлений
	 *
	 * @return количество нерассмотренных заявлений
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 * @throws PermissionDeniedException
	 *             если нет прав на получение количества нерассмотренных
	 *             заявлений
	 */
	public int getUnConsideredStatementsCount() {
		Collection<Statement> statements;
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		try {
			statements = service.loadStatements(EnumSet.of(StatementStatus.NOT_CONSIDERED));
		} catch (EJBAccessException e) {
			throw new PermissionDeniedException("Нет прав на получение количества нерассмотренных заявлений", e);
		}
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
	 * @throws PermissionDeniedException
	 *             если нет прав на рассмотрение заявлений
	 */
	public void approve(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		Statement statement;
		try {
			statement = service.approve(aStatement);
		} catch (EJBAccessException e) {
			throw new PermissionDeniedException("Нет прав на рассмотрение заявлений", e);
		}
		StatementConsideredEvent event = new StatementConsideredEvent(statement);
		event.setInitiator(createCurrentEventInitiator());
		fireEvent(event);
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
	 *
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
	 * @throws PermissionDeniedException
	 *             если нет прав на рассмотрение заявлений
	 */
	public void reject(Statement aStatement) throws StatementAlreadyConsideredException {
		Objects.requireNonNull(aStatement);
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		Statement statement;
		try {
			statement = service.reject(aStatement);
		} catch (EJBAccessException e) {
			throw new PermissionDeniedException("Нет прав на рассмотрение заявлений", e);
		}
		StatementConsideredEvent event = new StatementConsideredEvent(statement);
		event.setInitiator(createCurrentEventInitiator());
		fireEvent(event);
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
	 * Возвращает входящие заявления
	 *
	 * @return входящие заявления
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 * @throws PermissionDeniedException
	 *             если нет прав на рассмотрение заявлений
	 */
	public Collection<Statement> getIncomingStatements() {
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		try {
			return service.getIncomingStatements();
		} catch (EJBAccessException e) {
			throw new PermissionDeniedException("Нет прав на загрузку входящих заявлений", e);
		}
	}

	/**
	 * Возвращает количество не использованных дней отпуска в этом периоде
	 *
	 * @return количество дней
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getLeaveCount() {
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		return service.getLeaveCount(_currenUser);
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
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		return service.getOutgoingLeaveCount(_currenUser);
	}

	/**
	 * Возвращает дату начала следующего периода
	 *
	 * @return дата начала следующего периода
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public Date getNextLeaveStartDate() {
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		return service.getNextLeaveStartDate(_currenUser);
	}

	/**
	 * Логгер
	 */
	public static interface IHolidayCalculatorModelLogger {

		/**
		 * Логирует ошибку
		 *
		 * @param aMessage
		 *            текст
		 * @param aException
		 *            причина
		 */
		public void logError(String aMessage, Exception aException);

	}

	public ClientId getClientId() {
		return _clientId;
	}

	/**
	 * Возвращает все заявления пользователя
	 *
	 * @return заявления
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public Collection<Statement> getCurrentUserStatements() {
		IHolidayCalculatorService service = _servicesProvider.getHolidayCalculatorService();
		return service.getAllStatements(_currenUser);
	}

}
