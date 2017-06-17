/**
 *
 */
package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.iskandar.holiday.calculator.ui.document.HTMLContent;
import ru.iskandar.holiday.calculator.ui.document.IHTMLContentProvider;

/**
 * Просмотрщик HTML документа
 */
public class HTMLDocumentViewer {

	private final IHTMLContentProvider _contentProvider;

	/**
	 * Конструктор
	 */
	public HTMLDocumentViewer(IHTMLContentProvider aContentProvider) {
		Objects.requireNonNull(aContentProvider);
		_contentProvider = aContentProvider;
	}

	/** Браузер */
	private Browser _browser;

	public Control create(Composite aParent, FormToolkit aToolkit) {

		Composite main = aToolkit.createComposite(aParent, SWT.BORDER);
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		main.setLayout(layout);
		_browser = new Browser(main, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		_browser.setLayoutData(gd);

		refresh();
		return main;
	}

	public void refresh() {
		HTMLContent content = _contentProvider.getContent();
		String text = new String(content.getContent());
		_browser.setText(text);
	}

}
