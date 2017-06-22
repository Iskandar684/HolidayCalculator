package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;

/**
 *
 */
public interface IHolidayCalculatorReportService {

	public IReport generate(HolidayStatementEntry aEntry) throws HolidayCalculatorException;

}
