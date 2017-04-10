package ru.iskandar.holiday.calculator.ui.statement;

import java.util.Collection;

import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider;

/**
 * Поставщик заявлений
 */
public interface IStatementsProvider extends ILoadingProvider {

	/**
	 * Возвращает заявления
	 *
	 * @return заявления
	 * @throws IllegalStateException
	 *             если произошла ошибка при загрузке заявлений
	 * @see {@link #getLoadStatus()}
	 */
	public Collection<Statement> getStatements();

	public static interface IStatementsChangedListener {

		public void statementsChanged();
	}

	public void addStatementsChangedListener(IStatementsChangedListener aListener);

	public void removeStatementsChangedListener(IStatementsChangedListener aListener);
}
