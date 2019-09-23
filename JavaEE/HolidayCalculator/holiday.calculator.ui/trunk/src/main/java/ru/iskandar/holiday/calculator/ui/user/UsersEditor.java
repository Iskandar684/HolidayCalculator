package ru.iskandar.holiday.calculator.ui.user;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.StructuredViewer;
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

import ru.iskandar.holiday.calculator.service.model.user.UserId;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

public class UsersEditor extends EditorPart {

	public static final String EDITOR_ID = "holiday.calculator.ui.editor.users";

	private HolidayCalculatorModelProvider _modelProvider;

	private StructuredViewer _usersViewer;

	private UsersTableCreator _usersTable;

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

		_usersTable = new UsersTableCreator(_modelProvider);
		_usersViewer = _usersTable.create(sash);
		_usersViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Optional<UserId> userId = ((ViewUsersEditorInput) getEditorInput()).getUserToSelect();
		userId.ifPresent(_usersTable::setSelection);
	}

	@Override
	public void setFocus() {
		_usersViewer.getControl().setFocus();
	}

	public void setSelection(UserId aUserId) {
		_usersTable.setSelection(aUserId);
	}

}
