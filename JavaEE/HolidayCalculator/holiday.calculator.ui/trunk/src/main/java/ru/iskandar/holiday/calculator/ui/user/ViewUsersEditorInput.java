/**
 *
 */
package ru.iskandar.holiday.calculator.ui.user;

import java.util.Objects;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

/**
 *
 */
public class ViewUsersEditorInput implements IEditorInput {

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public ViewUsersEditorInput(HolidayCalculatorModelProvider aModelProvider) {
		Objects.requireNonNull(aModelProvider);
		_modelProvider = aModelProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getToolTipText() {
		return null;
	}

	/**
	 * @return the modelProvider
	 */
	public HolidayCalculatorModelProvider getModelProvider() {
		return _modelProvider;
	}

}
