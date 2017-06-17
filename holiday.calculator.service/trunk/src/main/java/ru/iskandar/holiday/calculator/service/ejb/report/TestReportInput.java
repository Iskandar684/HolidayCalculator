package ru.iskandar.holiday.calculator.service.ejb.report;

import java.util.Collections;
import java.util.Map;

import ru.iskandar.holiday.calculator.report.service.api.IReportInput;
import ru.iskandar.holiday.calculator.report.service.api.IReportParameter;
import ru.iskandar.holiday.calculator.report.service.api.ReportType;

/**
 *
 */
public class TestReportInput implements IReportInput {

	/** Путь к файлу с исходным кодом отчета */
	private static final String URL_TO_REPORT_FILE = "diagramReport.rptdesign";

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
		System.out.println("путь к отчету " + path);
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, IReportParameter> getParameters() {
		return Collections.emptyMap();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClassLoader getClassLoader() {
		return this.getClass().getClassLoader();
	}

}
