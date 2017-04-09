package ru.iskandar.holiday.calculator.ui;

import org.eclipse.nebula.widgets.datechooser.DateChooser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class Utils {

	public static DateChooser createDateChooser(Composite aParent, int aStyle) {
		DateChooser dateChooser = new DateChooser(aParent, aStyle);

		FontData fontData = new FontData();
		fontData.setHeight(20);
		final Font font = new Font(Display.getDefault(), fontData);

		dateChooser.setFont(font);

		dateChooser.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent aE) {
				font.dispose();
			}
		});
		dateChooser.layout();
		return dateChooser;
	}

}
