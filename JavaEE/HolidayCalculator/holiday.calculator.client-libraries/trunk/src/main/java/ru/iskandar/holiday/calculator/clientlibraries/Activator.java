package ru.iskandar.holiday.calculator.clientlibraries;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ru.iskandar.holiday.calculator.clientlibraries.authentification.AuthentificationDialog;
import ru.iskandar.holiday.calculator.clientlibraries.authentification.ConnectionParams;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "holiday.calculator.client-libraries"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext aContext) throws Exception {
		super.start(aContext);
		plugin = this;
		ConnectionParams params = ConnectionParams.getInstance();
		AuthentificationDialog dialog = new AuthentificationDialog(Display.getDefault().getActiveShell(), params);
		if (IDialogConstants.OK_ID != dialog.open()) {
			PlatformUI.getWorkbench().close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext aContext) throws Exception {
		plugin = null;
		super.stop(aContext);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
