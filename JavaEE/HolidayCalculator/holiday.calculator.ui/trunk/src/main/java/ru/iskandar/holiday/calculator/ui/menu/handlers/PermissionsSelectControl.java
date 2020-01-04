package ru.iskandar.holiday.calculator.ui.menu.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ru.iskandar.holiday.calculator.service.ejb.PermissionId;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

/**
 * Элемент управления выбора полномочий.
 */
public class PermissionsSelectControl {

	/** Полномочия */
	private final List<PermissionId> _permissions;

	/** Выбранные полномочия */
	private final Set<PermissionId> _selectedPermissions = new HashSet<>();

	/** Инструментарий для создания пользовательского интерфейса */
	private FormToolkit _toolkit;

	/**
	 * Конструктор
	 */
	public PermissionsSelectControl(HolidayCalculatorModelProvider aModelProvider) {
		Objects.requireNonNull(aModelProvider);
		_permissions = new ArrayList<>(aModelProvider.getModel().getAllPermissions());
	}

	/**
	 * Создаёт элемент управления.
	 *
	 * @param aParent
	 *            родительский элемент
	 * @return корневой элемент управления
	 */
	public Control create(Composite aParent) {
		_toolkit = new FormToolkit(aParent.getDisplay());
		_toolkit.setBackground(aParent.getBackground());
		aParent.addDisposeListener(event -> _toolkit.dispose());
		Composite main = _toolkit.createComposite(aParent);
		main.setLayout(new FillLayout());
		for (PermissionId permission : _permissions) {
			createButton(main, permission);
		}
		return main;
	}

	public Button createButton(Composite aParent, PermissionId permission) {
		Button bt = _toolkit.createButton(aParent, permission.getId(), SWT.CHECK);
		bt.setSelection(_selectedPermissions.contains(bt));
		bt.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent aE) {
				boolean add = bt.getSelection();
				if (add) {
					_selectedPermissions.add(permission);
				} else {
					_selectedPermissions.remove(permission);
				}
			}
		});
		return bt;
	}

	public Set<PermissionId> getSelectedPermissions() {
		return Collections.unmodifiableSet(_selectedPermissions);
	}

}
