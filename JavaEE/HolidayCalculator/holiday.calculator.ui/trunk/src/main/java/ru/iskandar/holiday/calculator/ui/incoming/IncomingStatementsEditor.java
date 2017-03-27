package ru.iskandar.holiday.calculator.ui.incoming;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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

import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.incoming.StatementReviewForm.IStatementProvider;

public class IncomingStatementsEditor extends EditorPart {

	public static final String EDITOR_ID = "holiday.calculator.ui.editor.incoming";

	private HolidayCalculatorModelProvider _holidayModelProvider;

	private TableViewer _statementsViewer;

	/**
	 * Конструктор
	 */
	public IncomingStatementsEditor() {
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
		_holidayModelProvider = ((IncomingStatementsEditorInput) aInput).getModelProvider();
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

		SashForm sash = new SashForm(main, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		StatementsTableCreator creator = new StatementsTableCreator(_holidayModelProvider);
		_statementsViewer = creator.create(sash);
		_statementsViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//
		StatementReviewForm reviewForm = new StatementReviewForm(_holidayModelProvider, new StatementProvider());
		reviewForm.create(sash);
	}

	private class StatementProvider implements IStatementProvider {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Statement getStatement() {

			ISelection sel = _statementsViewer.getSelection();
			if (sel instanceof IStructuredSelection) {
				Object firstElement = ((IStructuredSelection) sel).getFirstElement();
				return (Statement) firstElement;
			}
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {

	}

}