package ru.iskandar.holiday.calculator.ui.document;

import java.util.Objects;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import ru.iskandar.holiday.calculator.ui.takeholiday.HTMLContentProvider;

/**
 *
 */
public class DocumentEditorInput implements IEditorInput {

	/** Поставщик содержимого документа заявления */
	private final HTMLContentProvider _statementDocumentContentProvider;

	/**
	 * Конструктор
	 */
	public DocumentEditorInput(HTMLContentProvider aStatementDocumentContentProvider) {
		_statementDocumentContentProvider = Objects.requireNonNull(aStatementDocumentContentProvider);
	}

	@Override
	public <T> T getAdapter(Class<T> aAdapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	public HTMLContentProvider getStatementDocumentContentProvider() {
		return _statementDocumentContentProvider;
	}

}
