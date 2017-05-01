package ru.iskandar.holiday.calculator.ui.menu.handlers;

import java.util.Objects;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.ILoadListener;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.user.UsersEditor;
import ru.iskandar.holiday.calculator.ui.user.ViewUsersEditorInput;

/**
 * Обработчик элемента меню просмотра пользователей
 */
public class ViewUsersAction extends Action {

	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public ViewUsersAction(HolidayCalculatorModelProvider aProvider) {
		Objects.requireNonNull(aProvider);
		_modelProvider = aProvider;
		setText(Messages.openUsersActionText);
		aProvider.addListener(new IHolidayCalculatorModelListener() {

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
						update();
					}
				});

			}
		});
		_modelProvider.addLoadListener(new ILoadListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void loadStatusChanged() {
				update();
			}
		});
		update();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new ViewUsersEditorInput(_modelProvider), UsersEditor.EDITOR_ID, true, IWorkbenchPage.MATCH_ID);
		} catch (PartInitException e) {
			throw new RuntimeException("Ошибка открытия формы просмотра пользователей", e);
		}
	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		boolean enabled = false;
		if (LoadStatus.LOADED.equals(_modelProvider.getLoadStatus())) {
			enabled = _modelProvider.getModel().canCreateUser();
		}
		setEnabled(enabled);
	}

}
