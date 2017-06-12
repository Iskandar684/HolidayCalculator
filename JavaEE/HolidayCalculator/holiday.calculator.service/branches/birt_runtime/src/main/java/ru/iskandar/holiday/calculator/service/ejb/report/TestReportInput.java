package ru.iskandar.holiday.calculator.service.ejb.report;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
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
		return "/home/iskandar/wildfly-10.1.0.Final/standalone/deployments/report/diagramReport.rptdesign";
	}

	/**
	 * Преобразовать относительный путь к абсолютной (Метод в разработке!!!)
	 *
	 * @param aUrl
	 *            относительный путь
	 * @see org.eclipse.osgi.service.urlconversion.URLConverter#toFileURL(java.net
	 *      .URL)
	 * @return абсолютный путь
	 * @throws IOException
	 *             если не удалось найти файл
	 */
	private URL toFileURL(URL aUrl) throws IOException {
		// FIXME разобраться!!!
		try {
			Class<?>[] parameterTypes = null;
			URLConnection connection = aUrl.openConnection();
			Class<? extends URLConnection> cl = connection.getClass();
			Method m = cl.getMethod("getFileURL", parameterTypes);
			Object[] args = null;
			return (URL) m.invoke(connection, args);
		} catch (Exception e) {
			e.printStackTrace();
			String err = String.format(
					"The URL %1$s could not be extracted probably due to insufficient permissions or insufficient disk space",
					aUrl);
			throw new IOException(err);
		}
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
