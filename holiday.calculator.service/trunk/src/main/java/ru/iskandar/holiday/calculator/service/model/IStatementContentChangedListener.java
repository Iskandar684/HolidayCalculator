package ru.iskandar.holiday.calculator.service.model;

/**
 * Слушатель изменения содержания заявления
 */
public interface IStatementContentChangedListener {

	/**
	 * Обрабатывает изменение содержания заявления
	 * 
	 * @param aEvent
	 *            событие
	 */
	public void documentContentChanged(StatementContentChangedEvent aEvent);

}
