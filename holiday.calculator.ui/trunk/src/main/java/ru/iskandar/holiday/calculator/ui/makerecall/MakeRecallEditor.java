package ru.iskandar.holiday.calculator.ui.makerecall;

import java.util.Date;
import java.util.Objects;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.nebula.widgets.datechooser.DateChooser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

import ru.iskandar.holiday.calculator.service.model.ClientId;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorListenerAdapter;
import ru.iskandar.holiday.calculator.service.model.MakeRecallStatementBuilder;
import ru.iskandar.holiday.calculator.service.model.StatementSendedEvent;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.Utils;

/**
 * Редактор подачи заявления на отпуск
 */
public class MakeRecallEditor extends EditorPart {

	/** Идентификатор редактора */
	public static final String EDITOR_ID = "ru.iskandar.makerecall.editor";

	/** Инструментарий для создания пользовательского интерфейса */
	private FormToolkit _toolkit;

	/** Поставщик модели */
	private HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public MakeRecallEditor() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
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
	public void init(IEditorSite site, IEditorInput aInput) throws PartInitException {
		setSite(site);
		setInput(aInput);
		_modelProvider = ((MakeRecallEditorInput) aInput).getModelProvider();
		Objects.requireNonNull(_modelProvider);
	}

	/**
	 * Слушатель модели
	 */
	private class HolidayCalculatorModelListener extends HolidayCalculatorListenerAdapter {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void recallStatementSended(StatementSendedEvent aEvent) {
			ClientId currentClientId = _modelProvider.getModel().getClientId();
			ClientId eventClientId = aEvent.getInitiator() != null ? aEvent.getInitiator().getClientId() : null;
			if (currentClientId.equals(eventClientId)) {
				Display.getDefault().asyncExec(new Runnable() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						MakeRecallEditor.this.close();
					}

				});
			}
		}

	}

	/**
	 * Закрывает редактор
	 */
	private void close() {
		getEditorSite().getPage().closeEditor(this, false);
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
		_toolkit = new FormToolkit(aParent.getDisplay());
		Composite main = _toolkit.createComposite(aParent);
		GridLayout mainLauout = new GridLayout();
		mainLauout.marginWidth = 0;
		mainLauout.marginHeight = 0;
		main.setLayout(mainLauout);
		SashForm sash = new SashForm(main, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLeft(sash);
		createRight(sash);

		final HolidayCalculatorModelListener modelListener = new HolidayCalculatorModelListener();
		_modelProvider.addListener(modelListener);
		main.addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_modelProvider.removeListener(modelListener);
			}
		});
	}

	/**
	 * Создает Панель просмотра печатной формы заявления
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createRight(Composite aParent) {
		Composite main = _toolkit.createComposite(aParent, SWT.BORDER);
		GridLayout leftLayout = new GridLayout();
		leftLayout.marginWidth = 0;
		leftLayout.marginHeight = 0;
		main.setLayout(leftLayout);
		return main;
	}

	/**
	 * Создает элементы управления подачи заявления на отгул
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createLeft(Composite aParent) {
		Composite main = _toolkit.createComposite(aParent);
		final int columns = 2;
		GridLayout leftLayout = new GridLayout(columns, false);
		leftLayout.marginHeight = 0;
		main.setLayout(leftLayout);
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		Label header = _toolkit.createLabel(main, Messages.makeRecallFormHeader);
		header.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		DateChooser dateChooser = Utils.createDateChooser(main, SWT.MULTI);
		new RecallDateChooserPM(dateChooser, _modelProvider);

		dateChooser.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		Button makeRecallBt = _toolkit.createButton(main, Messages.makeRecallBt, SWT.NONE);
		makeRecallBt.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));
		MakeRecallStatementBuilder builder = _modelProvider.getModel().getRecallStatementBuilder();
		builder.addDate(new Date());
		new SendRecallStatementButtonPM(makeRecallBt, builder);
		return main;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
	}

}
