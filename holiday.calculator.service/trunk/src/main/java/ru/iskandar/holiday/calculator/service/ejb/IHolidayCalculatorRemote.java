package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;
import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.User;

/**
 * Сервис учета отгулов
 */
public interface IHolidayCalculatorRemote {

	/** JNDI имя */
	public static String JNDI_NAME = "holiday.calculator.service/HolidayCalculatorBean!ru.iskandar.holiday.calculator.service.ejb.IHolidayCalculatorRemote";

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