package ru.iskandar.holiday.calculator.ui.takeholiday;

import java.util.Objects;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.part.EditorPart;

import ru.iskandar.holiday.calculator.service.model.TakeHolidayStatementBuilder;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.Messages;

/** Редактор подачи заявления на отгул */
public class TakeHolidayEditor extends EditorPart {

	/** Идентификатор редактора */
	public static final String EDITOR_ID = "ru.iskandar.holiday.take.editor";

	/** Инструментарий для создания пользовательского интерфейса */
	private FormToolkit _toolkit;

	/** Поставщик модели */
	private HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public TakeHolidayEditor() {
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
		_modelProvider = ((TakeHolidayEditorInput) aInput).getModelProvider();
		Objects.requireNonNull(_modelProvider);
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
		TakeHolidayStatementBuilder builder = _modelProvider.getModel().createHolidayStatementBuilder();
		Composite main = _toolkit.createComposite(aParent);
		final int columns = 2;
		GridLayout leftLayout = new GridLayout(columns, false);
		leftLayout.marginHeight = 0;
		main.setLayout(leftLayout);
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		Label header = _toolkit.createLabel(main, Messages.getHolidayFormHeader);
		header.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		DateTime dateTime = new DateTime(main, SWT.CALENDAR);
		dateTime.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));

		Button byHolidaysBt = _toolkit.createButton(main, Messages.getHolidayByHolidayDays, SWT.RADIO);
		byHolidaysBt.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		new ByHolidaysButtonPM(byHolidaysBt, builder);

		int days = _modelProvider.getModel().getHolidaysQuantity();
		String daysAsStr = String.format("(%s)", days);
		Hyperlink link = _toolkit.createHyperlink(main, daysAsStr, SWT.NONE);
		link.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		Button byOwnBt = _toolkit.createButton(main, Messages.getHolidayByOwn, SWT.RADIO);
		byOwnBt.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, columns, 1));
		new ByOwnButtonPM(byOwnBt, builder);

		Button takeHolidayBt = _toolkit.createButton(main, Messages.takeHoliday, SWT.NONE);
		takeHolidayBt.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, columns, 1));
		_toolkit.createLabel(main, Messages.EMPTY, SWT.NONE)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, columns, 1));
		new SendHolidayStatementButtonPM(takeHolidayBt, builder);
		return main;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
	}

}
