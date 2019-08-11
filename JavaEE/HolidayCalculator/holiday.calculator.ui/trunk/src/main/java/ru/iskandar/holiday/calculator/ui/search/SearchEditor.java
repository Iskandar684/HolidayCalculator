package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.dataconnection.ISearchResult;
import ru.iskandar.holiday.calculator.dataconnection.SearchConnector;
import ru.iskandar.holiday.calculator.dataconnection.SearchException;

/**
 * Представление поиска.
 */
public class SearchEditor extends EditorPart {

	/** Идентификатор представления */
	public static final String ID = "ru.iskandar.holiday.calculator.ui.search.SearchView";

	/** Строка поиска */
	private Text _searchText;

	/** Таблица с результатами поиска. */
	private SearchResultTable _searchResultTable;

	private GridData _searchControlLayoutData;

	private final GridData _searchResultTableLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);

	private Control _searchControl;

	@Override
	public void createPartControl(Composite aParent) {
		Composite main = new Composite(aParent, SWT.NONE);
		GridLayout mainLayout = new GridLayout();
		main.setLayout(mainLayout);
		_searchControl = createSearchControl(main);
		_searchControlLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		_searchControl.setLayoutData(_searchControlLayoutData);
		_searchResultTable = new SearchResultTable();
		_searchResultTable.create(main);
		_searchResultTableLayoutData.exclude = true;
		_searchResultTable.getControl().setLayoutData(_searchResultTableLayoutData);
		_searchResultTable.getControl().setVisible(false);
	}

	private Control createSearchControl(Composite aParent) {
		Composite main = new Composite(aParent, SWT.NONE);
		GridLayout mainLayout = new GridLayout(2, false);
		mainLayout.marginWidth = 100;
		main.setLayout(mainLayout);
		_searchText = new Text(main, SWT.BORDER);
		_searchText.setMessage("Поиск...");
		GridData layoutData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		_searchText.setLayoutData(layoutData);
		_searchText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent aE) {
				if (SWT.CR == aE.character) {
					search(_searchText.getText());
				}
			}

		});
		Button searchBt = new Button(main, SWT.NONE);
		searchBt.setText("Найти");
		searchBt.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent aE) {
				search(_searchText.getText());
			}
		});
		return main;
	}

	private void search(String aText) {
		SearchConnector connection = new SearchConnector();
		ISearchResult result;
		try {
			result = connection.search(aText);
		} catch (SearchException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, getClass().getName(), e.getMessage(), e));
			return;
		}
		showSearchResult(result);
	}

	private void showSearchResult(ISearchResult aSearchResult) {
		_searchResultTable.setInput(aSearchResult);
		_searchResultTableLayoutData.exclude = false;
		_searchResultTable.getControl().setVisible(true);
		GridData layoutData = new GridData(SWT.FILL, SWT.TOP, true, false);
		_searchControl.setLayoutData(layoutData);
		_searchResultTable.getControl().getParent().layout();
		_searchControl.getParent().layout();
	}

	@Override
	public void setFocus() {
		_searchText.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor aMonitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite aSite, IEditorInput aInput) throws PartInitException {
		setSite(aSite);
		setInput(aInput);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
