package ru.iskandar.holiday.calculator.report.service.api;

/**
 * Исключение сервиса генерации отчета
 */
public class ReportServiceException extends Exception {

    /**
     * Идентификатор
     */
    private static final long serialVersionUID = -5580583355326638123L;

    /**
     * Конструктор
     * 
     * @param aMessage сообщение
     */
    public ReportServiceException(String aMessage) {
        super(aMessage);
    }

    /**
     * Конструктор
     * 
     * @param aMessage сообщение
     * @param aCause причина
     */
    public ReportServiceException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

}
