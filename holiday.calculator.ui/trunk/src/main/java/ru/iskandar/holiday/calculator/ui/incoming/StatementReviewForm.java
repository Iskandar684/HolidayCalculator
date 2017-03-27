package ru.iskandar.holiday.calculator.ui.incoming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.iskandar.holiday.calculator.service.ejb.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.Messages;

/**
 * Форма рассмотрения заявления
 */
public class StatementReviewForm {

	/** Инструментарий для создания пользовательского интерфейса */
	private FormToolkit _toolkit;

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	private final IStatementProvider _statementProvider;

	/**
	 * Конструктор
	 */
	public StatementReviewForm(HolidayCalculatorModelProvider aModelProvider, IStatementProvider aStatementProvider) {
		Objects.requireNonNull(aModelProvider);
		Objects.requireNonNull(aStatementProvider);
		_modelProvider = aModelProvider;
		_statementProvider = aStatementProvider;
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
		return main;
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

		DateTime dateTime = new DateTime(main, SWT.CALENDAR);
		dateTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		Button approveBt = _toolkit.createButton(main, Messages.approveBt, SWT.NONE);
		approveBt.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, true));
		approveBt.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				try {
					_modelProvider.getModel().approve(getStatement());
				} catch (StatementAlreadyConsideredException e) {
					Statement st = e.getStatement();
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
					Date considerDate = st.getConsiderDate();
					String text = NLS.bind(Messages.statementAlreadyConsideredByOtherDialogText,
							formatter.format(considerDate));
					if (_modelProvider.getModel().getCurrentUser().equals(st.getConsider())) {
						text = NLS.bind(Messages.statementAlreadyConsideredByYouDialogText,
								formatter.format(considerDate));
					}
					MessageDialog.openWarning(main.getShell(), Messages.statementAlreadyConsideredDialogTitle, text);
				}
			}
		});

		Button rejectBt = _toolkit.createButton(main, Messages.rejectBt, SWT.NONE);
		rejectBt.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true));
		rejectBt.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				try {
					_modelProvider.getModel().reject(getStatement());
				} catch (StatementAlreadyConsideredException e) {
					Statement st = e.getStatement();
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
					Date considerDate = st.getConsiderDate();
					String text = NLS.bind(Messages.statementAlreadyConsideredByOtherDialogText,
							formatter.format(considerDate));
					if (_modelProvider.getModel().getCurrentUser().equals(st.getConsider())) {
						text = NLS.bind(Messages.statementAlreadyConsideredByYouDialogText,
								formatter.format(considerDate));
					}
					MessageDialog.openWarning(main.getShell(), Messages.statementAlreadyConsideredDialogTitle, text);
				}
			}
		});
		return main;
	}

	private Statement getStatement() {
		return _statementProvider.getStatement();
	}

	public static interface IStatementProvider {

		public Statement getStatement();
	}

}
