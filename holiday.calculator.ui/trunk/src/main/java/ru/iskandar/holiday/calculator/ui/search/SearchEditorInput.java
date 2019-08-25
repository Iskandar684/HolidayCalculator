package ru.iskandar.holiday.calculator.ui.search;

import java.util.Objects;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

/**
 * @author Искандар
 *
 */
public class SearchEditorInput implements IEditorInput {

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор.
	 */
	public SearchEditorInput(HolidayCalculatorModelProvider aModelProvider) {
		Objects.requireNonNull(aModelProvider);
		_modelProvider = aModelProvider;
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
}
