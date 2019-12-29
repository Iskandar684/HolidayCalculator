package ru.iskandar.holiday.calculator.ui.document;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

import ru.iskandar.holiday.calculator.ui.takeholiday.HTMLContentProvider;
import ru.iskandar.holiday.calculator.ui.takeholiday.HTMLDocumentViewer;

public class DocumentEditor extends EditorPart {

	/** Идентификатор редактора */
	public static final String EDITOR_ID = "ru.iskandar.holiday.calculator.ui.DocumentEditor";

	/** Инструментарий для создания пользовательского интерфейса */
	private FormToolkit _toolkit;

	/** Поставщик содержимого документа заявления */
	private HTMLContentProvider _statementDocumentContentProvider;

	/** Просмотрщик HTML документа */
	private HTMLDocumentViewer _docViewer;

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
		_statementDocumentContentProvider = ((DocumentEditorInput) aInput).getStatementDocumentContentProvider();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite aParent) {
		_toolkit = new FormToolkit(aParent.getDisplay());
		Composite main = _toolkit.createComposite(aParent, SWT.BORDER);
		GridLayout leftLayout = new GridLayout();
		leftLayout.marginWidth = 0;
		leftLayout.marginHeight = 0;
		main.setLayout(leftLayout);
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_docViewer = new HTMLDocumentViewer(_statementDocumentContentProvider);
		Control control = _docViewer.create(main, _toolkit);
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		control.addDisposeListener(aE -> _toolkit.dispose());
	}

	@Override
	public void setFocus() {
		_docViewer.setFocus();
	}

}
