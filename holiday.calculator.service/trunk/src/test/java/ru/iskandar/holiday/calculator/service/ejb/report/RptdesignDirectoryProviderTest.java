package ru.iskandar.holiday.calculator.service.ejb.report;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RptdesignDirectoryProviderTest {

	/**
	 *
	 */
	@Test
	public void testGetDirectory() {
		RptdesignDirectoryProvider provider = new RptdesignDirectoryProvider();

		System.setProperty("jboss.server.data.dir", "/home/iskandar/wildfly-10.1.0.Final/standalone/data");

		String expected = "/home/iskandar/wildfly-10.1.0.Final/standalone/deployments/report/";
		String res = provider.getDirectory();
		assertEquals(expected, res);
	}

	/**
	 *
	 */
	@Test
	public void testGetDirectory2() {
		RptdesignDirectoryProvider provider = new RptdesignDirectoryProvider();

		System.setProperty("jboss.server.data.dir", "/home/data/wildfly-10.1.0.Final/standalone/data");

		String expected = "/home/data/wildfly-10.1.0.Final/standalone/deployments/report/";
		String res = provider.getDirectory();
		assertEquals(expected, res);
	}

}
