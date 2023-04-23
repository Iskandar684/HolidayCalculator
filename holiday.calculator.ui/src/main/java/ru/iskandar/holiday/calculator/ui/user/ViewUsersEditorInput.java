/**
 *
 */
package ru.iskandar.holiday.calculator.ui.user;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import ru.iskandar.holiday.calculator.service.model.user.UserId;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

/**
 *
 */
public class ViewUsersEditorInput implements IEditorInput {

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	private UserId _userToSelect;

	/**
	 * Конструктор
	 */
	public ViewUsersEditorInput(HolidayCalculatorModelProvider aModelProvider) {
		_modelProvider = Objects.requireNonNull(aModelProvider);
	}

	/**
	 * Конструктор
	 */
	public ViewUsersEditorInput(HolidayCalculatorModelProvider aModelProvider, UserId aUserToSelect) {
		_modelProvider = Objects.requireNonNull(aModelProvider);
		_userToSelect = aUserToSelect;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return null;
	}

	/**
	 * Возвращает поставщика модели.
	 *
	 * @return поставщика модели
	 */
	public HolidayCalculatorModelProvider getModelProvider() {
		return _modelProvider;
	}

	public Optional<UserId> getUserToSelect() {
		return Optional.ofNullable(_userToSelect);
	}

}
