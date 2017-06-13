package ru.iskandar.holiday.calculator.report.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

import ru.iskandar.holiday.calculator.report.service.api.IReportParameter;

/**
 * Генератор отчетов
 */
public class ReportGenerator {

	/**
	 * Генерировать HTML отчет
	 *
	 * @param aUrlToRptdesignFile
	 *            путь в BIRT файлу .rptdesign
	 * @param aParameters
	 *            параметры отчета или {@code null}
	 * @param aClassLoader
	 *            загрузчик классов для BIRT
	 * @return HTML отчет в виде массива байт
	 * @throws BirtException
	 *             ошибка генерации отчета
	 */
	@SuppressWarnings("unchecked")
	public static byte[] generateHTMLReport(String aUrlToRptdesignFile, Map<String, IReportParameter> aParameters,
			ClassLoader aClassLoader) throws BirtException {
		Objects.requireNonNull(aUrlToRptdesignFile, "Не указан путь к файлу с описанием отчета (.rptdesign)");
		Objects.requireNonNull(aParameters, "Не указана карта параметров отчета");
		Objects.requireNonNull(aClassLoader, "Не указан загрузчик классов");

		EngineConfig config = new EngineConfig();
		HashMap<Object, Object> map = config.getAppContext();
		map.put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, aClassLoader);
		config.setAppContext(map);

		Platform.startup(config);

		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

		IReportEngine engine = factory.createReportEngine(config);
		IReportRunnable design = engine.openReportDesign(aUrlToRptdesignFile);
		IRunAndRenderTask task = engine.createRunAndRenderTask(design);
		task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, aClassLoader);
		HTMLRenderOption options = new HTMLRenderOption();
		options.setOutputFormat(HTMLRenderOption.HTML);
		options.setEmbeddable(false);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		options.setOutputStream(out);
		task.setRenderOption(options);

		for (IReportParameter param : aParameters.values()) {
			if (param != null) {
				String id = param.getId();
				Object value = param.getValue();
				String displayText = param.getDisplayText();
				task.setParameter(id, value, displayText);
			}
		}

		task.run();
		task.close();
		byte[] report = out.toByteArray();
		return report;
	}

}
