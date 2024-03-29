package ru.iskandar.holiday.calculator.ui.takeleave;

import java.util.Objects;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;

/**
 * Входные данные редактора {@link TakeLeaveEditor}
 */
public class TakeLeaveEditorInput implements IEditorInput {

	/** Поставщик модели */
	private final HolidayCalculatorModelProvider _modelProvider;

	/**
	 * Конструктор
	 */
	public TakeLeaveEditorInput(HolidayCalculatorModelProvider aModelProvider) {
		Objects.requireNonNull(aModelProvider);
		_modelProvider = aModelProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the modelProvider
	 */
	public HolidayCalculatorModelProvider getModelProvider() {
		return _modelProvider;
	}

}
