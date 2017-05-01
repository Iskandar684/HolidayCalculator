package ru.iskandar.holiday.calculator.ui.user;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

public class UsersEditor extends EditorPart {

	public static final String EDITOR_ID = "holiday.calculator.ui.editor.users";

	private HolidayCalculatorModelProvider _modelProvider;

	private TableViewer _usersViewer;

	public UsersEditor() {
	}

	@Override
	public void doSave(IProgressMonitor aMonitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite aSite, IEditorInput aInput) throws PartInitException {
		setInput(aInput);
		setSite(aSite);
		_modelProvider = ((ViewUsersEditorInput) aInput).getModelProvider();
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
		FormToolkit toolkit = new FormToolkit(Display.getCurrent());
		Composite main = toolkit.createComposite(aParent);
		GridLayout mainLayout = new GridLayout();
		main.setLayout(mainLayout);
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		SashForm sash = new SashForm(main, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		UsersTableCreator creator = new UsersTableCreator(_modelProvider);
		_usersViewer = creator.create(sash);
		_usersViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	@Override
	public void setFocus() {
		_usersViewer.getControl().setFocus();
	}

}
