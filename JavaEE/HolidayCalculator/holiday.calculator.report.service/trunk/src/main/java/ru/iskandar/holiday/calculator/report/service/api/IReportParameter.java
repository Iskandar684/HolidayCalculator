package ru.iskandar.holiday.calculator.report.service.api;

/**
 * Интерфейс для параметра отчета
 */
public interface IReportParameter {

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
    public Object getValue();

    /**
     * Получить отображаемый текст
     * 
     * @return текст или {@code null}
     */
    public String getDisplayText();

}
