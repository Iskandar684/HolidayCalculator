package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.report.service.api.IReportInput;
import ru.iskandar.holiday.calculator.report.service.api.IReportServiceLocal;
import ru.iskandar.holiday.calculator.report.service.api.ReportServiceException;
import ru.iskandar.holiday.calculator.service.ejb.report.TestReportInput;

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
	public IReport generate() throws HolidayCalculatorException {
		IReportInput input = new TestReportInput();
		System.out.println("UrlToReportDesignFile  " + input.getUrlToReportDesignFile());
		try {
			return _reportServiceLocal.generateReport(input);
		} catch (ReportServiceException e) {
			throw new HolidayCalculatorException(String.format("Ошибка генерации отчета для входных данных %s", input),
					e);
		}
	}

}
