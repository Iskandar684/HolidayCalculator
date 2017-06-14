package ru.iskandar.holiday.calculator.service.ejb.report;

/**
 * Поставщик директории с файлами с описанием отчета (*.rptdesign)
 */
public class RptdesignDirectoryProvider {

	/**
	 * Возвращает директорию, где лежат файлы rptdesign
	 *
	 * @return директория
	 */
	public String getDirectory() {
		String key = "jboss.server.data.dir";
		String prop = System.getProperty("jboss.server.data.dir");
		if (prop == null) {
			throw new IllegalStateException(String.format("Отсутствует свойство %s", key));
		}
		String data = "data";
		prop = prop.substring(0, prop.length() - data.length());
		String reportDir = "deployments/report/";
		prop += reportDir;
		return prop;
	}

}
