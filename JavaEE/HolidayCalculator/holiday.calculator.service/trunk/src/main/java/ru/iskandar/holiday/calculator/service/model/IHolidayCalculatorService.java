package ru.iskandar.holiday.calculator.service.model;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import javax.ejb.EJBAccessException;

import ru.iskandar.holiday.calculator.service.ejb.InvalidStatementException;
import ru.iskandar.holiday.calculator.service.ejb.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.ejb.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.ejb.StatementNotFoundException;

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
	 */
	public Set<Statement> loadStatements(EnumSet<StatementStatus> aStatuses);

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
	public Statement approve(Statement aStatement) throws StatementAlreadyConsideredException;

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
	public Statement reject(Statement aStatement) throws StatementAlreadyConsideredException;

	/**
	 * Подает заявление на отгул рассмотрение
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
	public HolidayStatement sendStatement(HolidayStatement aStatement) throws StatementAlreadySendedException;

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
	public LeaveStatement sendStatement(LeaveStatement aStatement) throws StatementAlreadySendedException;

	/**
	 * Подает заявление на отзыв на рассмотрение
	 *
	 * @param aStatement
	 *            заявление на отзыв
	 * @return заявление на отзыв
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws NullPointerException
	 *             если aStatement {@code null}
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	public RecallStatement sendStatement(RecallStatement aStatement) throws StatementAlreadySendedException;

	/**
	 * Возвращает количество отгулов у указанного пользователя
	 *
	 * @param aUser
	 *            пользователь
	 * @return количество отгулов
	 */
	public int getHolidaysQuantity(User aUser);

	/**
	 * Возвращает количество исходящих дней отгула. Это количество дней, на
	 * которое уменьшется количество общее дней отгула, после того как заявление
	 * на отгул будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отгула
	 */
	public int getOutgoingHolidaysQuantity(User aUser);

	/**
	 * Возвращает количество приходящих отгулов. Это количество дней, на которое
	 * будет увеличино общее количество отгулов, после того как засчитают отзыв.
	 *
	 * @return количество приходящих отгулов
	 */
	public int getIncomingHolidaysQuantity(User aUser);

	/**
	 * Возвращает входящие заявления
	 *
	 * @return входящие заявления
	 */
	public Collection<Statement> getIncomingStatements();

	/**
	 * Возвращает количество не использованных дней отпуска в этом периоде
	 *
	 * @return количество дней
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getLeaveCount(User aUser);

	/**
	 * Возвращает количество исходящих дней отпуска. Это количество дней, на
	 * которое уменьшется количество дней отпуска в этом периоде, после того как
	 * заявление на отпуск будет принят.
	 *
	 * @return не отрицательное количество исходящих дней отпуска.
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public int getOutgoingLeaveCount(User aUser);

	/**
	 * Возвращает дату начала следующего периода
	 *
	 * @return дата начала следующего периода
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public Date getNextLeaveStartDate(User aUser);

}
