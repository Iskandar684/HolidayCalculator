/**
 *
 */
package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
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

	private class LoadListener implements ILoadListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void loadStatusChanged() {
			Display.getDefault().asyncExec(new Runnable() {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void run() {
					refresh();
				}
			});
		}
	}

	/** Браузер */
	private Browser _browser;

	private void initListeners() {
		final LoadListener loadListener = new LoadListener();
		_contentProvider.addLoadListener(loadListener);
		_browser.addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_contentProvider.removeLoadListener(loadListener);

			}
		});
	}

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
		initListeners();
		refresh();
		return main;
	}

	public void refresh() {
		LoadStatus status = _contentProvider.getLoadStatus();
		if (LoadStatus.LOADED.equals(status)) {
			HTMLContent content = _contentProvider.getContent();
			String text = new String(content.getContent());
			_browser.setText(text);
		}
	}

}
