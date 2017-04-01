package ru.iskandar.holiday.calculator.service.model;

import java.util.Collection;
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
	 * Подает заявление на рассмотрение
	 *
	 * @param aStatement
	 *            заявление
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

}
