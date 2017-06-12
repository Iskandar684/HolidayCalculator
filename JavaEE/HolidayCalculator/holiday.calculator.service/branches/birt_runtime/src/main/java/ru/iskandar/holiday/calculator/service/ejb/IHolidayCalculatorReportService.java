package ru.iskandar.holiday.calculator.service.ejb;

import ru.iskandar.holiday.calculator.report.service.api.IReport;

/**
 *
 */
public interface IHolidayCalculatorReportService {

	public IReport generate() throws HolidayCalculatorException;

}
