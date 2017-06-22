package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.iskandar.holiday.calculator.report.service.api.IReportInput;
import ru.iskandar.holiday.calculator.report.service.api.IReportParameter;
import ru.iskandar.holiday.calculator.report.service.api.ReportParameter;
import ru.iskandar.holiday.calculator.report.service.api.ReportType;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;

/**
 * Входные данные для генерации документа заявления на отгул
 */
public class StatementDocumentReportInput implements IReportInput {

	/** Путь к файлу с исходным кодом отчета */
	private static final String URL_TO_REPORT_FILE = "statementDocument.rptdesign";

	private HolidayStatementReportParameterFactory _paramFactory;

	/**
	 *
	 */
	public StatementDocumentReportInput(HolidayStatementEntry aEntry) {
		Objects.requireNonNull(aEntry, "Не указано содержание заявления");
		_paramFactory = new EntryBasedHolidayStatementReportParamFactory(aEntry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReportType getType() {
		return ReportType.HTML;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUrlToReportDesignFile() {
		RptdesignDirectoryProvider provider = new RptdesignDirectoryProvider();
		String path = String.format("%s%s", provider.getDirectory(), URL_TO_REPORT_FILE);
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, IReportParameter<?>> getParameters() {
		Map<String, IReportParameter<?>> params = new HashMap<>();
		HolidayStatementReportParameter param = _paramFactory.create();
		String id = "statement";
		params.put(id, new ReportParameter<HolidayStatementReportParameter>(id, param, param.toString()));
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClassLoader getClassLoader() {
		return this.getClass().getClassLoader();
	}

}
