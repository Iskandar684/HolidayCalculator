package ru.iskandar.holiday.calculator.report.service.api;

/**
 * Интерфейс для отчета
 */
public interface IReport {

    // TODO добавить наименование отчета

    /**
     * Получить тип отчета
     * 
     * @return тип отчета
     */
    public ReportType getType();

    /**
     * Получить содердимое отчета
     * 
     * @return содердимое отчета
     */
    public byte[] getContent();

}
