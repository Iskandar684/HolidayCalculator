package ru.iskandar.holiday.calculator.ui.outgoing;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.statement.StatementsTableCreator;

public class OutgoingStatementsEditor extends EditorPart {

	public static final String EDITOR_ID = "holiday.calculator.ui.editor.outgoing";

	private HolidayCalculatorModelProvider _holidayModelProvider;

	private StructuredViewer _statementsViewer;

	private StatementsTableCreator _statementsTable;

	/**
	 * Конструктор
	 */
	public OutgoingStatementsEditor() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSave(IProgressMonitor aMonitor) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSaveAs() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IEditorSite aSite, IEditorInput aInput) throws PartInitException {
		setInput(aInput);
		setSite(aSite);
		_holidayModelProvider = ((OutgoingStatementsEditorInput) aInput).getModelProvider();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDirty() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite aParent) {
		FormToolkit toolkit = new FormToolkit(Display.getCurrent());
		Composite main = toolkit.createComposite(aParent);
		GridLayout mainLayout = new GridLayout();
		main.setLayout(mainLayout);
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_statementsTable = new StatementsTableCreator(new CurrentUserStatementsProvider(_holidayModelProvider));
		_statementsViewer = _statementsTable.create(main);
		_statementsViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public void showStatement(StatementId aStatementId) {
		_statementsTable.showStatement(aStatementId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		_statementsViewer.getControl().setFocus();
	}

}
