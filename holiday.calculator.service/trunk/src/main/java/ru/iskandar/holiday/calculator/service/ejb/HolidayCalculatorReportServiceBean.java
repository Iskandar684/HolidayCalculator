package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.report.service.api.IReportInput;
import ru.iskandar.holiday.calculator.report.service.api.IReportServiceLocal;
import ru.iskandar.holiday.calculator.report.service.api.ReportServiceException;
import ru.iskandar.holiday.calculator.service.ejb.report.StatementDocumentReportInput;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;

/**
 *
 */
@Stateless
@Local(IHolidayCalculatorReportService.class)
public class HolidayCalculatorReportServiceBean implements IHolidayCalculatorReportService {

	/** Локальный интерфейс сервиса генерации отчетов */
	@EJB
	private IReportServiceLocal _reportServiceLocal;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IReport generate(HolidayStatementEntry aEntry) throws HolidayCalculatorException {
		IReportInput input = new StatementDocumentReportInput(aEntry);
		try {
			return _reportServiceLocal.generateReport(input);
		} catch (ReportServiceException e) {
			throw new HolidayCalculatorException(String.format("Ошибка генерации отчета для входных данных %s", input),
					e);
		}
	}

}
