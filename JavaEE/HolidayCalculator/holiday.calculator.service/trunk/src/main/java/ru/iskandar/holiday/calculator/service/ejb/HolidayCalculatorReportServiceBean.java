package ru.iskandar.holiday.calculator.service.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.report.service.api.IReportInput;
import ru.iskandar.holiday.calculator.report.service.api.IReportServiceLocal;
import ru.iskandar.holiday.calculator.report.service.api.ReportServiceException;
import ru.iskandar.holiday.calculator.service.ejb.report.LeaveDocumentReportInput;
import ru.iskandar.holiday.calculator.service.ejb.report.RecallDocumentReportInput;
import ru.iskandar.holiday.calculator.service.ejb.report.StatementDocumentReportInput;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;

/**
 * Сервис печатной формы заявлений.
 */
@Stateless
@Local(IHolidayCalculatorReportService.class)
public class HolidayCalculatorReportServiceBean implements IHolidayCalculatorReportService {

	/** Локальный интерфейс сервиса генерации отчетов */
	@EJB
	private IReportServiceLocal _reportServiceLocal;

	@Override
	public IReport generate(HolidayStatementEntry aEntry) throws HolidayCalculatorException {
		IReportInput input = new StatementDocumentReportInput(aEntry);
		return generate(input);
	}

	@Override
	public IReport generate(LeaveStatementEntry aEntry) throws HolidayCalculatorException {
		IReportInput input = new LeaveDocumentReportInput(aEntry);
		return generate(input);
	}

	@Override
	public IReport generate(RecallStatementEntry aEntry) throws HolidayCalculatorException {
		IReportInput input = new RecallDocumentReportInput(aEntry);
		return generate(input);
	}

	private IReport generate(IReportInput aInput) throws HolidayCalculatorException {
		try {
			return _reportServiceLocal.generateReport(aInput);
		} catch (ReportServiceException e) {
			throw new HolidayCalculatorException(String.format("Ошибка генерации отчета для входных данных %s", aInput),
					e);
		}
	}

}
