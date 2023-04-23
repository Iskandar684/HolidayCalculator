package ru.iskandar.holiday.calculator.report.service.api;

/** Интерфейс для сервисов генерации отчета */
public interface IReportService {

    /**
     * Сгенерировать отчет
     * 
     * @param aReportInput входные параметры для генерации отчета
     * @return отчет
     * @throws ReportServiceException вызывается при ошибке генерации отчета
     */
    public IReport generateReport(IReportInput aReportInput)
            throws ReportServiceException;

}
