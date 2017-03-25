package ru.iskandar.holiday.calculator.service.ejb;

import java.util.EnumSet;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModelLoadException;
import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;

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
	 * @throws HolidayCalculatorException
	 *             если произошла ошибка при одобрении
	 */
	public Statement approve(Statement aStatement) throws HolidayCalculatorException;

	/**
	 * Отклоняет заявление
	 *
	 * @param aStatement
	 *            заявление
	 * @return заявление
	 * @throws HolidayCalculatorException
	 *             если произошла ошибка при отклонении
	 */
	public Statement reject(Statement aStatement) throws HolidayCalculatorException;

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
	 */
	public HolidayStatement sendStatement(HolidayStatement aStatement) throws StatementAlreadySendedException;
}