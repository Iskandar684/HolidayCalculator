package ru.iskandar.holiday.calculator.ui;

import java.text.SimpleDateFormat;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.service.model.User;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.outgoing.OutgoingStatementsEditor;
import ru.iskandar.holiday.calculator.ui.outgoing.OutgoingStatementsEditorInput;

/**
 * Форма отображения атрибутов пользователя
 */
public class UserAttributesForm extends Composite {

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;
	/***/
	private Hyperlink _lcLink;
	/***/
	private Label _holidaysQuantityLabel;
	/***/
	private Hyperlink _outHQLink;
	/***/
	private Hyperlink _hqLink;
	/***/
	private Hyperlink _inHQLink;
	/***/
	private Label _fioLabel;
	/***/
	private Hyperlink _outLCLink;

	private final LinkSelectionHandler _linkSelectionHandler = new LinkSelectionHandler();

	/** Инструментарий для создания пользовательского интерфейса */
	private final FormToolkit _formToolkit;

	/**
	 * Конструктор
	 *
	 * @param aParent
	 *            родитель
	 * @param aUserProvider
	 *            поставщик модели
	 */
	public UserAttributesForm(Composite aParent, FormToolkit aFormToolkit,
			HolidayCalculatorModelProvider aUserProvider) {
		super(aParent, SWT.NONE);
		Objects.requireNonNull(aUserProvider);
		Objects.requireNonNull(aFormToolkit);
		_modelProvider = aUserProvider;
		_formToolkit = aFormToolkit;
		create();
		initListeners();
		refresh();
	}

	/**
	 * Создает пользовательский интерфейс
	 */
	private void create() {
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		setLayout(mainLayout);
		_formToolkit.createLabel(this, Messages.EMPTY).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		_fioLabel = _formToolkit.createLabel(this, Messages.EMPTY);
		_fioLabel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		_formToolkit.createLabel(this, Messages.EMPTY).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		createHolidaysQuantityLabel(this).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		createLeaveCountLabel(this).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		createNextLeaveStartDate(this).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	Hyperlink _nextLeaveStartDateLink;

	/**
	 * Создает строку "Дата начала следующего периода"
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createNextLeaveStartDate(Composite aParent) {

		Composite main = _formToolkit.createComposite(aParent);
		int columns = 2;
		GridLayout mainLayout = new GridLayout(columns, false);
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		main.setLayout(mainLayout);
		Label nextDateLabel = _formToolkit.createLabel(main, Messages.nextLeaveStartDate);
		nextDateLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		_nextLeaveStartDateLink = _formToolkit.createHyperlink(main, Messages.EMPTY, SWT.NONE);

		return main;
	}

	/**
	 * @return the nextLeaveStartDateLink
	 */
	Hyperlink getNextLeaveStartDateLink() {
		return _nextLeaveStartDateLink;
	}

	/**
	 * Создает строку "Количество дней отпуска"
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createLeaveCountLabel(Composite aParent) {

		Composite main = _formToolkit.createComposite(aParent);
		final int columns = 3;

		GridLayout mainLayout = new GridLayout(columns, false);
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		main.setLayout(mainLayout);

		Label leaveCountLabel = _formToolkit.createLabel(main, Messages.leaveCount);
		leaveCountLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		_lcLink = _formToolkit.createHyperlink(main, Messages.EMPTY, SWT.NONE);
		_lcLink.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		_lcLink.addHyperlinkListener(_linkSelectionHandler);

		_outLCLink = _formToolkit.createHyperlink(main, Messages.EMPTY, SWT.NONE);
		_outLCLink.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		_outHQLink.addHyperlinkListener(_linkSelectionHandler);

		return main;
	}

	/**
	 * Создает строку "Количество отгулов"
	 *
	 * @param aParent
	 *            родитель
	 * @return корневой элемент управления
	 */
	private Composite createHolidaysQuantityLabel(Composite aParent) {
		Composite main = _formToolkit.createComposite(aParent);
		final int columns = 4;

		GridLayout mainLayout = new GridLayout(columns, false);
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		main.setLayout(mainLayout);

		_holidaysQuantityLabel = _formToolkit.createLabel(main, Messages.holidaysQuantity);
		_holidaysQuantityLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		_hqLink = _formToolkit.createHyperlink(main, Messages.EMPTY, SWT.NONE);
		_hqLink.setLayoutData(new GridData(SWT.CENTER, SWT.LEFT, false, false));
		_hqLink.addHyperlinkListener(_linkSelectionHandler);

		_outHQLink = _formToolkit.createHyperlink(main, Messages.EMPTY, SWT.NONE);
		_outHQLink.setLayoutData(new GridData(SWT.CENTER, SWT.LEFT, false, false));
		_outHQLink.addHyperlinkListener(_linkSelectionHandler);

		_inHQLink = _formToolkit.createHyperlink(main, Messages.EMPTY, SWT.NONE);
		_inHQLink.setLayoutData(new GridData(SWT.CENTER, SWT.LEFT, false, false));
		_inHQLink.addHyperlinkListener(_linkSelectionHandler);
		return main;
	}

	/**
	 * Подписка на события
	 */
	private void initListeners() {
		final ILoadListener loadListener = new ILoadListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void loadStatusChanged() {
				Display.getDefault().asyncExec(new Runnable() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						UserAttributesForm.this.refresh();
					}

				});

			}

		};
		_modelProvider.addLoadListener(loadListener);
		final IHolidayCalculatorModelListener holidayModelListener = new IHolidayCalculatorModelListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(HolidayCalculatorEvent aAEvent) {
				Display.getDefault().asyncExec(new Runnable() {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						UserAttributesForm.this.refresh();
					}

				});

			}
		};

		_modelProvider.addListener(holidayModelListener);

		addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_modelProvider.removeLoadListener(loadListener);
				_modelProvider.removeListener(holidayModelListener);
			}
		});
	}

	/**
	 * Обновляет UI
	 */
	void refresh() {
		boolean loaded = LoadStatus.LOADED.equals(_modelProvider.getLoadStatus());
		String fio = Messages.EMPTY;
		String hqStr = Messages.EMPTY;
		String dateAsStr = Messages.EMPTY;
		String outHQStr = Messages.EMPTY;
		String inHQStr = Messages.EMPTY;
		String lcStr = Messages.EMPTY;
		String outLCStr = Messages.EMPTY;
		boolean outHQLinkVisible = false;
		boolean inHQLinkVisible = false;
		boolean outLCVisible = false;
		if (loaded) {
			HolidayCalculatorModel model = _modelProvider.getModel();
			User user = model.getCurrentUser();
			Objects.requireNonNull(user);
			fio = String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getPatronymic());
			int hq = model.getHolidaysQuantity();
			hqStr = String.valueOf(hq);
			int outHQ = model.getOutgoingHolidaysQuantity();
			outHQLinkVisible = outHQ != 0;
			int inHQ = model.getIncomingHolidaysQuantity();
			inHQLinkVisible = inHQ != 0;
			int outLC = model.getOutgoingLeaveCount();

			int lc = model.getLeaveCount();
			dateAsStr = new SimpleDateFormat("dd.MM.yyyy").format(model.getNextLeaveStartDate());
			outHQStr = String.format("(-%s)", outHQ);

			inHQStr = String.format("(+%s)", inHQ);
			lcStr = String.valueOf(lc);
			outLCStr = String.format("(-%s)", outLC);
			outLCVisible = outLC != 0;
		}

		_fioLabel.setText(fio);
		_hqLink.setText(hqStr);
		_outHQLink.setText(outHQStr);

		_outHQLink.setVisible(outHQLinkVisible);
		((GridData) _outHQLink.getLayoutData()).exclude = !outHQLinkVisible;

		_inHQLink.setText(inHQStr);
		_inHQLink.setVisible(inHQLinkVisible);
		((GridData) _inHQLink.getLayoutData()).exclude = !inHQLinkVisible;

		_lcLink.setText(lcStr);
		_outLCLink.setText(outLCStr);
		_outLCLink.setVisible(outLCVisible);
		((GridData) _outLCLink.getLayoutData()).exclude = !outLCVisible;

		_nextLeaveStartDateLink.setText(dateAsStr);

		_fioLabel.getParent().layout();
		_hqLink.getParent().layout();
		_outHQLink.getParent().layout();
		_lcLink.getParent().layout();
		_nextLeaveStartDateLink.getParent().layout();
	}

	/**
	 *
	 */
	private class LinkSelectionHandler extends HyperlinkAdapter {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void linkActivated(HyperlinkEvent aE) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
						new OutgoingStatementsEditorInput(_modelProvider), OutgoingStatementsEditor.EDITOR_ID, true,
						IWorkbenchPage.MATCH_ID);
			} catch (PartInitException e) {
				throw new RuntimeException("Ошибка открытия формы подачи заявления на отгул", e);
			}
		}
	}

}
