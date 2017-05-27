package ru.iskandar.holiday.calculator.report.service;

import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.eclipse.birt.core.exception.BirtException;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.report.service.api.IReportInput;
import ru.iskandar.holiday.calculator.report.service.api.IReportParameter;
import ru.iskandar.holiday.calculator.report.service.api.IReportServiceLocal;
import ru.iskandar.holiday.calculator.report.service.api.ReportServiceException;
import ru.iskandar.holiday.calculator.report.service.api.ReportType;

/**
 *
 */
@Local(IReportServiceLocal.class)
@Stateless
public class BirtReportServiceBean implements IReportServiceLocal {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IReport generateReport(IReportInput aReportInput) throws ReportServiceException {
		String err = RequiredParamsValidator.validate(aReportInput);
		if (err != null) {
			throw new ReportServiceException(err);
		}
		IReport report = null;
		try {
			ReportType type = aReportInput.getType();
			if (!ReportType.HTML.equals(type)) {
				// TODO доработать сервис для остальных форматов
				throw new ReportServiceException(String.format("Тип отчета %s не поддерживается", type));
			}
			Map<String, IReportParameter> params = aReportInput.getParameters();
			String url = aReportInput.getUrlToReportDesignFile();
			ClassLoader classLoader = aReportInput.getClassLoader();
			if (classLoader == null) {
				throw new ReportServiceException("Не указан загрузчик классов");
			}
			byte[] data = ReportGenerator.generateHTMLReport(url, params, classLoader);
			report = new Report(type, data);
		} catch (BirtException e) {
			throw new ReportServiceException("Ошибка генерации отчета", e);
		}

		return report;
	}

}
