package ru.iskandar.holiday.calculator.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ReloadHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		ModelProviderHolder.getInstance().getModelProvider().asynReload();
		return null;
	}

}
