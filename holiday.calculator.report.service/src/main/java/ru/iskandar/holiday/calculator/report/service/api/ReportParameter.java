package ru.iskandar.holiday.calculator.report.service.api;

import java.io.Serializable;

/**
 * Параметр отчета
 */
public class ReportParameter<V extends Serializable> implements IReportParameter<V> {

	/** Идентификатор параметра */
	private final String _id;

	/** Значение параметра */
	private final V _value;

	/** Отображаемый текст */
	private final String _text;

	/**
	 * Конструктор
	 *
	 * @param aId
	 *            идентификатор параметра
	 * @param aValue
	 *            значение параметра
	 * @param aDisplayText
	 *            отображаемый текст
	 */
	public ReportParameter(String aId, V aValue, String aDisplayText) {
		_id = aId;
		_value = aValue;
		_text = aDisplayText;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return _id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public V getValue() {
		return _value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayText() {
		return _text;
	}

}
