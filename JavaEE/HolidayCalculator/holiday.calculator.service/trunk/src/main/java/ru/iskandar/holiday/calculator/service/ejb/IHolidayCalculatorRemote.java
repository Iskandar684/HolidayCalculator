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

	public Statement approve(Statement aStatement);

	public Statement reject(Statement aStatement);

	public HolidayStatement sendStatement(HolidayStatement aStatement);
}