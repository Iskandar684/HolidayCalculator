package ru.iskandar.holiday.calculator.ui;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.takeholiday.TakeHolidayButtonPM;
import ru.iskandar.holiday.calculator.ui.takeleave.TakeLeaveButtonPM;

/**
 * Панель навигации
 */
public class NavigationViewPart extends ViewPart {

	public static final String ID = "ru.iskandar.holiday.calculator.ui.NavigationViewPart";

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	/** Менеджер расположения элементов управления */
	private final StackLayout _stackLayout = new StackLayout();

	/** Корневой элемент управления "Идет загрузка" */
	private Control _loadingCtl;

	/** Корневой элемент управления "Ошибка загрузки" */
	private Control _loadErrCtl;

	/** Корневой элемент управления "Успешная загрузка" */
	private Control _loadedCtl;

	/**
	 * Конструктор
	 */
	public NavigationViewPart() {
		_modelProvider = ModelProviderHolder.getInstance().getModelProvider();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite aParent) {
		final FormToolkit toolkit = new FormToolkit(Display.getDefault());
		Composite main = toolkit.createComposite(aParent);
		main.setLayout(_stackLayout);

		_loadingCtl = createLoadingStub(main, toolkit);
		_loadErrCtl = createLoadErrorStub(main, toolkit);
		_loadedCtl = createMain(main, toolkit);

		final LoadListener loadListener = new LoadListener();
		_modelProvider.addLoadListener(loadListener);
		main.addDisposeListener(new DisposeListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDisposed(DisposeEvent aE) {
				_modelProvider.removeLoadListener(loadListener);
			}
		});
		refresh();
	}

	private class LoadListener implements ILoadListener {

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
					refresh();
				}

			});

		}

	}

	/**
	 * Создает загрушку в случае ошибки загрузки модели учета отгулов
	 *
	 * @param aParent
	 *            родитель
	 * @param aToolkit
	 *            инструментарий для создания пользовательского интерфейса
	 */
	private Composite createLoadErrorStub(Composite aParent, FormToolkit aToolkit) {
		final Composite main = aToolkit.createComposite(aParent, SWT.NONE);
		main.setLayout(new GridLayout());
		main.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		final Composite content = aToolkit.createComposite(main, SWT.NONE);
		final int columns = 2;
		content.setLayout(new GridLayout(columns, false));
		content.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		Label iconLabel = aToolkit.createLabel(content, Messages.EMPTY);
		iconLabel.setImage(Display.getDefault().getSystemImage(SWT.ICON_ERROR));
		iconLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		Label messLabel = aToolkit.createLabel(content, Messages.modelLoadError, SWT.WRAP);
		messLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		Button tryReloadBt = aToolkit.createButton(content, Messages.tryReloadModel, SWT.NONE);
		tryReloadBt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, columns, 1));
		tryReloadBt.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent aE) {
				_modelProvider.asynReload();
			}
		});
		return main;
	}

	/**
	 * Создает загрушку в случае долгой загрузки модели учета отгулов
	 *
	 * @param aParent
	 *            родитель
	 * @param aToolkit
	 *            инструментарий для создания пользовательского интерфейса
	 */
	private Composite createLoadingStub(Composite aParent, FormToolkit aToolkit) {
		final Composite main = aToolkit.createComposite(aParent, SWT.BORDER);
		final int columns = 2;
		main.setLayout(new GridLayout(columns, false));
		main.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		Label iconLabel = aToolkit.createLabel(main, Messages.EMPTY);
		iconLabel.setImage(Display.getDefault().getSystemImage(SWT.ICON_WORKING));
		iconLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		Label messLabel = aToolkit.createLabel(main, Messages.modelLoading, SWT.WRAP);
		messLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		return main;
	}

	/**
	 * Создает основную форму
	 *
	 * @param aParent
	 *            родитель
	 * @param aToolkit
	 *            инструментарий для создания пользовательского интерфейса
	 */
	private Composite createMain(Composite aParent, FormToolkit aToolkit) {
		final Composite main = aToolkit.createComposite(aParent, SWT.BORDER);
		final int columns = 2;
		main.setLayout(new GridLayout(columns, false));
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		UserAttributesForm form = new UserAttributesForm(main, aToolkit, _modelProvider);
		form.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, columns, 1));

		DateTime dateTime = new DateTime(main, SWT.CALENDAR);
		dateTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, columns, 1));

		final int buttonHeight = 60;
		Button takeHolidayBt = aToolkit.createButton(main, Messages.getHoliday, SWT.NONE);
		GridData btGridData = new GridData(SWT.FILL, SWT.BOTTOM, true, false, columns, 1);
		btGridData.heightHint = buttonHeight;
		takeHolidayBt.setLayoutData(btGridData);
		new TakeHolidayButtonPM(takeHolidayBt, _modelProvider);

		Button makeRecallBt = aToolkit.createButton(main, Messages.makeRecall, SWT.NONE);
		btGridData = new GridData(SWT.FILL, SWT.BOTTOM, true, false);
		btGridData.heightHint = buttonHeight;
		makeRecallBt.setLayoutData(btGridData);

		Button getLeaveBt = aToolkit.createButton(main, Messages.getLeave, SWT.NONE);
		btGridData = new GridData(SWT.FILL, SWT.BOTTOM, true, false);
		btGridData.heightHint = buttonHeight;
		getLeaveBt.setLayoutData(btGridData);
		new TakeLeaveButtonPM(getLeaveBt, _modelProvider);
		return main;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		if (_stackLayout.topControl != null) {
			_stackLayout.topControl.setFocus();
		}
	}

	/**
	 * Обновляет UI
	 */
	private void refresh() {
		LoadStatus st = _modelProvider.getLoadStatus();
		switch (st) {
		case LOADED:
			_stackLayout.topControl = _loadedCtl;
			break;

		case LOAD_ERROR:
			_stackLayout.topControl = _loadErrCtl;
			break;

		case LOADING:
			_stackLayout.topControl = _loadingCtl;
			break;

		default:
			throw new IllegalStateException(String.format("Статус загрузки %s не поддерживается", st));
		}
		Objects.requireNonNull(_stackLayout.topControl);
		_stackLayout.topControl.getParent().layout();
	}

}
