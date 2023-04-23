package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;

/**
 * Сервис печатной формы заявлений.
 */
public interface IHolidayCalculatorReportService {

	/**
	 * Формирует печатную форму заявления на отгул.
	 *
	 * @param aEntry
	 *            заявление на отгул
	 * @return печатную форму заявления на отгул
	 * @throws HolidayCalculatorException
	 *             в случае ошибки формирования
	 */
	IReport generate(HolidayStatementEntry aEntry) throws HolidayCalculatorException;

	/**
	 * Формирует печатную форму заявления на отпуск.
	 *
	 * @param aEntry
	 *            заявление на отпуск
	 * @return печатную форму заявления на отпуск
	 * @throws HolidayCalculatorException
	 *             в случае ошибки формирования
	 */
	IReport generate(LeaveStatementEntry aEntry) throws HolidayCalculatorException;

	/**
	 * Формирует печатную форму заявления на отзыв.
	 *
	 * @param aEntry
	 *            заявление на отзыв
	 * @return печатную форму заявления на отзыв
	 * @throws HolidayCalculatorException
	 *             в случае ошибки формирования
	 */
	IReport generate(RecallStatementEntry aEntry) throws HolidayCalculatorException;

}
