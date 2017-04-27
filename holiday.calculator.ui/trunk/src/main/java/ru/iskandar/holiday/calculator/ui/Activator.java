package ru.iskandar.holiday.calculator.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ru.iskandar.holiday.calculator.dataconnection.ClientConnector;

public class Activator implements BundleActivator {

	/** Идентификатор плагина */
	public static final String PLUGIN_ID = "ru.iskandar.holiday.calculator.ui";

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		new ClientConnector().authenticate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
