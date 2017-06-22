package ru.iskandar.holiday.calculator.report.service.api;

import java.util.Map;

/**
 * Интерфейс для указания входных данных для генерации отчета
 */
public interface IReportInput {

	/**
	 * Получить тип отчета
	 *
	 * @return тип отчета
	 */
	public ReportType getType();

	/**
	 * Получить путь к файлу с исходным кодом отчета
	 *
	 * @return путь к файлу с исходным кодом отчета
	 */
	public String getUrlToReportDesignFile();

	/**
	 * Получить параметры отчета
	 *
	 * @return карта параметров отчета, где
	 *         <p>
	 *         ключ-идентификатор параметра {@link IReportParameter#getId()},
	 *         </p>
	 *         <p>
	 *         значение-параметр отчета {@link IReportParameter}
	 *         </p>
	 */
	public Map<String, IReportParameter<?>> getParameters();

	/**
	 * Возвращает загрузчик классов. Необходимо вернуть загрузчик класса,
	 * который будет использован в скрипте BIRT как POJO.
	 * <p>
	 * <b>При указании неправильного загрузчика или при использовании нескольких
	 * классов из BIRT возможно (если загрузчики классов отличаются) падение при
	 * генерации отчета с текстом is not a function, it is a object </b>
	 * </p>
	 *
	 * @return загрузчик классов или {@code null}, если в скрипте не
	 *         используются Java классы.
	 */
	public ClassLoader getClassLoader();

}
