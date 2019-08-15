package ru.iskandar.holiday.calculator.ui.incoming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.datechooser.DateChooser;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorListenerAdapter;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.ServiceLookupException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.StatementContentChangedEvent;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.service.model.permissions.PermissionDeniedException;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.ui.Activator;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.Utils;
import ru.iskandar.holiday.calculator.ui.document.HTMLContent;
import ru.iskandar.holiday.calculator.ui.takeholiday.HTMLContentProvider;
import ru.iskandar.holiday.calculator.ui.takeholiday.HTMLDocumentViewer;

/**
 * Форма рассмотрения заявления
 */
public class StatementReviewForm {

	/** Инструментарий для создания пользовательского интерфейса */
	private FormToolkit _toolkit;

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	private final IStatementProvider _statementProvider;

	private Button _approveBt;
	private Button _rejectBt;

	DateChooser _dateChooser;

	/** Поставщик содержимого документа заявления на отгул */
	private final HTMLContentProvider _statementDocumentContentProvider;

	private class DocContentLoader implements Callable<HTMLContent> {

		@Override
		public HTMLContent call() throws Exception {
			Statement<?> statement = _statementProvider.getStatement();
			if (statement == null) {
				return HTMLContent.EMPTY;
			}
			HolidayCalculatorModel model = _modelProvider.getModel();
			StatementDocument doc = model.getStatementDocument(statement.getId());
			return new HTMLContent(doc.getContent());
		}
	}

	/**
	 * Конструктор
	 */
	public StatementReviewForm(HolidayCalculatorModelProvider aModelProvider, IStatementProvider aStatementProvider) {
		_modelProvider = Objects.requireNonNull(aModelProvider);
		_statementProvider = Objects.requireNonNull(aStatementProvider);
		_statementDocumentContentProvider = new HTMLContentProvider(new DocContentLoader());
	}

	public Control create(Composite aParent) {
		_toolkit = new FormToolkit(aParent.getDisplay());
		Composite main = _toolkit.createComposite(aParent, SWT.BORDER);
		GridLayout mainLauout = new GridLayout();
		mainLauout.marginWidth = 0;
		mainLauout.marginHeight = 0;
		main.setLayout(mainLauout);
		SashForm sash = new SashForm(main, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLeft(sash);
		createRight(sash);

		initListeners(main);
		refresh();
		return main;
	}

	private void initListeners(Control aControl) {
		StatementChangedListener listener = new StatementChangedListener();
		_statementProvider.addStatementChangedListener(listener);
		LoadListener loadListener = new LoadListener();
		_modelProvider.addLoadListener(loadListener);
		aControl.addDisposeListener(aE -> {
			_statementProvider.removeStatementChangedListener(listener);
			_modelProvider.removeLoadListener(loadListener);
		});
	}

	private class LoadListener implements ILoadListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void loadStatusChanged() {
			Display.getDefault().asyncExec(() -> refresh());

		}

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
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		HTMLDocumentViewer viewer = new HTMLDocumentViewer(_statementDocumentContentProvider);
		Control control = viewer.create(main, _toolkit);
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		StatementContentChangedHandler contentChangedListener = new StatementContentChangedHandler();
		_modelProvider.addListener(contentChangedListener);
		control.addDisposeListener(aE -> _modelProvider.removeListener(contentChangedListener));
		return main;
	}

	private class StatementContentChangedHandler extends HolidayCalculatorListenerAdapter {

		@Override
		protected void statementContentChangedEvent(StatementContentChangedEvent aEvent) {
			_statementDocumentContentProvider.asynReload();
		}

	}

	/**
	 * Создает элементы управления подачи заявления на отгул
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createLeft(Composite aParent) {

		final Composite main = _toolkit.createComposite(aParent);
		final int columns = 2;
		GridLayout leftLayout = new GridLayout(columns, false);
		leftLayout.marginHeight = 0;
		main.setLayout(leftLayout);
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		Label header = _toolkit.createLabel(main, Messages.reviewHolidayStatementHeader);
		header.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		_dateChooser = Utils.createDateChooser(main, SWT.MULTI);
		_dateChooser.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));
		_dateChooser.setEnabled(false);

		_approveBt = _toolkit.createButton(main, Messages.approveBt, SWT.NONE);
		_approveBt.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, true));
		_approveBt.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				Statement<?> statement = getStatement();
				if (statement == null) {
					MessageDialog.openWarning(main.getShell(), Messages.statementForConsiderNotSelectedDialogTitle,
							Messages.statementForConsiderNotSelectedDialogText);
					return;
				}
				try {
					_modelProvider.getModel().approve(statement);
				} catch (StatementAlreadyConsideredException e) {
					Statement<?> st = e.getStatement();
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
					Date considerDate = st.getConsiderDate();
					String text = NLS.bind(Messages.statementAlreadyConsideredByOtherDialogText,
							formatter.format(considerDate));
					if (_modelProvider.getModel().getCurrentUser().equals(st.getConsider())) {
						text = NLS.bind(Messages.statementAlreadyConsideredByYouDialogText,
								formatter.format(considerDate));
					}
					MessageDialog.openWarning(main.getShell(), Messages.statementAlreadyConsideredDialogTitle, text);
				} catch (PermissionDeniedException e) {
					StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.permissionDeniedWhenConsideringDialogText, e));
					MessageDialog.openError(main.getShell(), Messages.permissionDeniedWhenConsideringDialogTitle,
							Messages.permissionDeniedWhenConsideringDialogText);
				} catch (ServiceLookupException e) {
					StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.lookupExceptionWhenConsideringDialogText, e));
					MessageDialog.openError(main.getShell(), Messages.lookupExceptionWhenConsideringDialogTitle,
							Messages.lookupExceptionWhenConsideringDialogText);
				}
			}
		});

		_rejectBt = _toolkit.createButton(main, Messages.rejectBt, SWT.NONE);
		_rejectBt.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true));
		_rejectBt.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				Statement<?> statement = getStatement();
				if (statement == null) {
					MessageDialog.openWarning(main.getShell(), Messages.statementForConsiderNotSelectedDialogTitle,
							Messages.statementForConsiderNotSelectedDialogText);
					return;
				}
				try {
					_modelProvider.getModel().reject(statement);
				} catch (StatementAlreadyConsideredException e) {
					Statement<?> st = e.getStatement();
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
					Date considerDate = st.getConsiderDate();
					String text = NLS.bind(Messages.statementAlreadyConsideredByOtherDialogText,
							formatter.format(considerDate));
					if (_modelProvider.getModel().getCurrentUser().equals(st.getConsider())) {
						text = NLS.bind(Messages.statementAlreadyConsideredByYouDialogText,
								formatter.format(considerDate));
					}
					MessageDialog.openWarning(main.getShell(), Messages.statementAlreadyConsideredDialogTitle, text);
				} catch (PermissionDeniedException e) {
					StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.permissionDeniedWhenConsideringDialogText, e));
					MessageDialog.openError(main.getShell(), Messages.permissionDeniedWhenConsideringDialogTitle,
							Messages.permissionDeniedWhenConsideringDialogText);
				} catch (ServiceLookupException e) {
					StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.lookupExceptionWhenConsideringDialogText, e));
					MessageDialog.openError(main.getShell(), Messages.lookupExceptionWhenConsideringDialogTitle,
							Messages.lookupExceptionWhenConsideringDialogText);
				}
			}
		});
		return main;
	}

	private Statement<?> getStatement() {
		return _statementProvider.getStatement();
	}

	/**
	 * Поставщик заявления
	 */
	public static interface IStatementProvider {

		/**
		 * Возвращает заявление
		 *
		 * @return заявление или {@code null}
		 */
		public Statement<?> getStatement();

		public void addStatementChangedListener(IStatementChangedListener aListener);

		public void removeStatementChangedListener(IStatementChangedListener aListener);
	}

	public static interface IStatementChangedListener {
		public void statementChanged();
	}

	private class StatementChangedListener implements IStatementChangedListener {

		@Override
		public void statementChanged() {
			_statementDocumentContentProvider.asynReload();
			refresh();
		}

	}

	/**
	 * Обновляет UI
	 */
	private void refresh() {
		boolean canApprove = false;
		boolean canReject = false;
		LoadStatus status = _modelProvider.getLoadStatus();
		_dateChooser.clearSelection();
		if (LoadStatus.LOADED.equals(status)) {
			Statement<?> statement = getStatement();
			if (statement != null) {
				HolidayCalculatorModel model = _modelProvider.getModel();
				canApprove = model.canApprove(statement);
				canReject = model.canReject(statement);
				Collection<Date> toSelect = new ArrayList<>();
				switch (statement.getStatementType()) {
				case HOLIDAY_STATEMENT:
					toSelect = ((HolidayStatement) statement).getDays();
					break;

				case LEAVE_STATEMENT:
					toSelect = ((LeaveStatement) statement).getLeaveDates();
					break;

				case RECALL_STATEMENT:
					toSelect = ((RecallStatement) statement).getRecallDates();
					break;
				}

				for (Date date : toSelect) {
					_dateChooser.setSelectedDate(date);
				}
			}
		}
		_approveBt.setEnabled(canApprove);
		_rejectBt.setEnabled(canReject);
	}

}
