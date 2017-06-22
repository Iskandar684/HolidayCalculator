package ru.iskandar.holiday.calculator.report.service.api;

import java.io.Serializable;

/**
 * Интерфейс для параметра отчета
 */
public interface IReportParameter<V extends Serializable> {

	/**
	 * Получить идентификатор параметра
	 * 
	 * @return идентификатор параметра
	 */
	public String getId();

	/**
	 * Получить значение параметра
	 * 
	 * @return значение параметра
	 */
	public V getValue();

	/**
	 * Получить отображаемый текст
	 * 
	 * @return текст или {@code null}
	 */
	public String getDisplayText();

}
