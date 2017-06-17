package ru.iskandar.holiday.calculator.service.model;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import javax.ejb.EJBAccessException;

import ru.iskandar.holiday.calculator.service.ejb.PermissionId;
import ru.iskandar.holiday.calculator.service.model.document.DocumentPreviewException;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.service.model.permissions.IHolidayCalculatorModelPermissions;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.user.NewUserEntry;
import ru.iskandar.holiday.calculator.service.model.user.NewUserNotValidException;
import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginAlreadyExistException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;

/**
 * Сервис учета отгулов
 */
public interface IHolidayCalculatorService extends IHolidayCalculatorModelPermissions {

	/**
	 * Загружает модель учета отгулов для текущего пользователя
	 *
	 * @return модель
	 * @throws HolidayCalculatorModelLoadException
	 *             ошибка загрузки модели
	 */
	public HolidayCalculatorModel loadHolidayCalculatorModel() throws HolidayCalculatorModelLoadException;

	/**
	 * Загружает заявления с указанными статусами
	 *
	 * @param aStatuses
	 *            статусы
	 * @return заявления
	 * @throws EJBAccessException
	 *             если нет прав на рассмотрение заявлений
	 * @throws NullPointerException
	 *             если aStatuses {@code null}
	 */
	public Collection<Statement<?>> loadStatements(EnumSet<StatementStatus> aStatuses);

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
	 * @throws EJBAccessException
	 *             если нет прав на рассмотрение заявлений
	 */
	public Statement<?> approve(Statement<?> aStatement) throws StatementAlreadyConsideredException;

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
	 * @throws EJBAccessException
	 *             если нет прав на рассмотрение заявлений
	 */
	public Statement<?> reject(Statement<?> aStatement) throws StatementAlreadyConsideredException;

	/**
	 * Создает и подает заявление на отгул на рассмотрение
	 *
	 * @param aStatement
	 *            заявление на отгул
	 * @return заявление
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws NullPointerException
	 *             если aStatement {@code null}
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	public HolidayStatement createHolidayStatement(HolidayStatementEntry aStatement)
			throws StatementAlreadySendedException;

	/**
	 * Подает заявление на отпуск на рассмотрение
	 *
	 * @param aStatement
	 *            заявление на отпуск
	 * @return заявление
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws NullPointerException
	 *             если aStatement {@code null}
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	public LeaveStatement createLeaveStatement(LeaveStatementEntry aStatement) throws StatementAlreadySendedException;

	/**
	 * Создает и подает заявление на отзыв на рассмотрение
	 *
	 * @param aStatement
	 *            содержание заявления на отзыв
	 * @return заявление на отзыв
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws NullPointerException
	 *             если aStatement {@code null}
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	public RecallStatement createRecallStatement(RecallStatementEntry aStatement)
			throws StatementAlreadySendedException;

	/**
	 * Возвращает количество отгулов у указанного пользователя
	 *
	 * @param aUser
	 *            пользователь
	 * @return количество отгулов
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 */
	public int getHolidaysQuantity(User aUser);

	/**
	 * Возвращает количество исходящих дней отгула. Это количество дней, на
	 * которое уменьшется количество общее дней отгула, после того как заявление
	 * на отгул будет принят.
	 *
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 * @return не отрицательное количество исходящих дней отгула
	 */
	public int getOutgoingHolidaysQuantity(User aUser);

	/**
	 * Возвращает количество приходящих отгулов. Это количество дней, на которое
	 * будет увеличино общее количество отгулов, после того как засчитают отзыв.
	 *
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 * @return количество приходящих отгулов
	 */
	public int getIncomingHolidaysQuantity(User aUser);

	/**
	 * Возвращает входящие заявления
	 *
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 * @return входящие заявления
	 */
	public Collection<Statement<?>> getIncomingStatements();

	/**
	 * Возвращает количество не использованных дней отпуска в этом периоде
	 *
	 * @return количество дней
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 */
	public int getLeaveCount(User aUser);

	/**
	 * Возвращает количество исходящих дней отпуска. Это количество дней, на
	 * которое уменьшется количество дней отпуска в этом периоде, после того как
	 * заявление на отпуск будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отпуска.
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 */
	public int getOutgoingLeaveCount(User aUser);

	/**
	 * Возвращает дату начала следующего периода
	 *
	 * @return дата начала следующего периода
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 */
	public Date getNextLeaveStartDate(User aUser);

	/**
	 * Возвращает все заявления пользователя
	 *
	 * @param aUser
	 *            пользователь
	 * @return заявления
	 * @throws NullPointerException
	 *             если aUser {@code null}
	 */
	public Collection<Statement<?>> getAllStatements(User aUser);

	/**
	 * Проверяет аутентификацию текущего пользователя
	 *
	 * @throws UserByLoginNotFoundException
	 *             если описание пользователя для вызывающего не найдено
	 */
	public void checkAuthentification() throws UserByLoginNotFoundException;

	/**
	 * Создает нового пользователя
	 *
	 * @param aNewUserEntry
	 *            описание создаваемого пользователя
	 * @param aNewUserPermissions
	 *            полномочия, которыми должен обладать создаваемый пользователь
	 * @return созданный пользователь
	 * @throws EJBAccessException
	 *             если у текущего пользователя нет прав на создание
	 *             пользователей
	 * @throws NullPointerException
	 *             если aNewUserEntry или aNewUserPermissions {@code null}
	 * @throws UserByLoginAlreadyExistException
	 *             если пользователь с указанным логином уже существует
	 * @throws NewUserNotValidException
	 *             если описание создавамого пользователя заполнено некорректно
	 * @see {@link #canCreateUser()}
	 */
	public User createUser(NewUserEntry aNewUserEntry, Set<PermissionId> aNewUserPermissions)
			throws UserByLoginAlreadyExistException;

	/**
	 * Возвращает возможность создания пользователя
	 *
	 * @return {@code true}, если текущему пользователю разрешено создавать
	 *         пользователей; {@code false}, если иначе
	 */
	public boolean canCreateUser();

	/**
	 * Возвращает всех пользователей
	 *
	 * @return коллекция пользователей
	 * @throws EJBAccessException
	 *             если у текущего пользователя нет прав на просмотр
	 *             пользователей
	 */
	public Collection<User> getAllUsers();

	/**
	 * Возвращает возможность загрузки всех пользователей
	 *
	 * @return {@code true}, если у текущего пользователя есть полномочие
	 *         просматривать других пользоватей; {@code false}, если иначе
	 */
	public boolean canViewUsers();

	/**
	 * Формирует документ заявления на отгул
	 *
	 * @param aEntry
	 *            содержание заявления на отгул
	 * @return документ заявления
	 * @throws DocumentPreviewException
	 *             если не удалось сформировать документ
	 */
	public StatementDocument preview(HolidayStatementEntry aEntry) throws DocumentPreviewException;

}
