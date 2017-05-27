package ru.iskandar.holiday.calculator.report.service;

import java.util.Objects;

import ru.iskandar.holiday.calculator.report.service.api.IReport;
import ru.iskandar.holiday.calculator.report.service.api.ReportType;

/** Отчет */
public class Report implements IReport {

	/** Тип отчета */
	private final ReportType _type;

	/** Содержимое отчета */
	private final byte[] _content;

	/**
	 * Конструктор
	 *
	 * @param aReportType
	 *            тип отчета
	 * @param aContent
	 *            содержимое отчета
	 */
	public Report(ReportType aReportType, byte[] aContent) {
		Objects.requireNonNull(aReportType);
		Objects.requireNonNull(aContent);
		_type = aReportType;
		_content = aContent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReportType getType() {
		return _type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getContent() {
		return _content;
	}

}
