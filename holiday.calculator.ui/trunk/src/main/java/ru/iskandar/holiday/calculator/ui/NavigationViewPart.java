package ru.iskandar.holiday.calculator.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import ru.iskandar.holiday.calculator.service.model.User;
import ru.iskandar.holiday.calculator.ui.UserAttributesForm.IUserProvider;
import ru.iskandar.holiday.calculator.ui.takeholiday.TakeHolidayButtonPM;

/**
 * Панель навигации
 */
public class NavigationViewPart extends ViewPart {

	public static final String ID = "ru.iskandar.holiday.calculator.ui.NavigationViewPart";

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider = new HolidayCalculatorModelProvider();

	/**
	 * Конструктор
	 */
	public NavigationViewPart() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite aParent) {
		final FormToolkit toolkit = new FormToolkit(Display.getDefault());
		// TODO не ждать загрузки модели в UI потоке! Ориентироваться по
		// _modelProvider.getLoadStatus();
		try {
			_modelProvider.getModel();
		} catch (Exception e) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.modelLoadError, e), StatusManager.LOG);
			createLoadErrorStub(aParent, toolkit);
			return;
		}
		createMain(aParent, toolkit);
	}

	/**
	 * Создает загрушку в случае ошибки загрузки модели учета отгулов
	 *
	 * @param aParent
	 *            родитель
	 * @param aToolkit
	 *            инструментарий для создания пользовательского интерфейса
	 */
	private void createLoadErrorStub(Composite aParent, FormToolkit aToolkit) {
		final Composite main = aToolkit.createComposite(aParent, SWT.BORDER);
		final int columns = 2;
		main.setLayout(new GridLayout(columns, false));
		main.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		Label iconLabel = aToolkit.createLabel(main, Messages.EMPTY);
		iconLabel.setImage(Display.getDefault().getSystemImage(SWT.ICON_ERROR));
		Label messLabel = aToolkit.createLabel(main, Messages.modelLoadError, SWT.WRAP);
		messLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
	}

	/**
	 * Создает основную форму
	 *
	 * @param aParent
	 *            родитель
	 * @param aToolkit
	 *            инструментарий для создания пользовательского интерфейса
	 */
	private void createMain(Composite aParent, FormToolkit aToolkit) {
		final Composite main = aToolkit.createComposite(aParent, SWT.BORDER);
		final int columns = 2;
		main.setLayout(new GridLayout(columns, false));
		main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		UserAttributesForm form = new UserAttributesForm(main, aToolkit, new FormUserProvider());
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
	}

	/**
	 * Поставщик пользователя для формы
	 *
	 */
	private class FormUserProvider implements IUserProvider {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public User getUser() {
			User user = _modelProvider.getModel().getCurrentUser();
			return user;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public LoadStatus getLoadStatus() {
			return _modelProvider.getLoadStatus();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addLoadListener(ILoadListener aLoadListener) {
			_modelProvider.addLoadListener(aLoadListener);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeLoadListener(ILoadListener aLoadListener) {
			_modelProvider.removeLoadListener(aLoadListener);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
