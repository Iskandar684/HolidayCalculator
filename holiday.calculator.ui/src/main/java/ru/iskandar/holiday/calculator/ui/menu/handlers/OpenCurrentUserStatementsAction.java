package ru.iskandar.holiday.calculator.ui.menu.handlers;

import java.util.Objects;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.Messages;
import ru.iskandar.holiday.calculator.ui.outgoing.OutgoingStatementsEditor;
import ru.iskandar.holiday.calculator.ui.outgoing.OutgoingStatementsEditorInput;

/**
 * Действие отображения заявлений текущего пользователя
 */
public class OpenCurrentUserStatementsAction extends Action {

	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public OpenCurrentUserStatementsAction(HolidayCalculatorModelProvider aProvider) {
		Objects.requireNonNull(aProvider);
		_modelProvider = aProvider;
		setText(Messages.openCurrentUserStatementsActionText);
		update();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new OutgoingStatementsEditorInput(_modelProvider), OutgoingStatementsEditor.EDITOR_ID, true,
					IWorkbenchPage.MATCH_ID);
		} catch (PartInitException e) {
			throw new RuntimeException("Ошибка открытия формы заявлений текущего пользователя", e);
		}
	}

	/**
	 * Обновляет состояние
	 */
	private void update() {
		boolean enabled = true;
		setEnabled(enabled);
	}
}
