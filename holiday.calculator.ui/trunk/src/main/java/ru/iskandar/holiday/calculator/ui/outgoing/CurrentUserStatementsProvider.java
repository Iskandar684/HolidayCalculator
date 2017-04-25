package ru.iskandar.holiday.calculator.ui.outgoing;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorEvent;
import ru.iskandar.holiday.calculator.service.model.HolidayCalculatorModel;
import ru.iskandar.holiday.calculator.service.model.IHolidayCalculatorModelListener;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.statement.IStatementsProvider;

/**
 *
 */
public class CurrentUserStatementsProvider implements IStatementsProvider {

	private final HolidayCalculatorModelProvider _holidayModelProvider;

	private final CopyOnWriteArraySet<IStatementsChangedListener> _listeners = new CopyOnWriteArraySet<>();

	/**
	 * Конструктор
	 */
	public CurrentUserStatementsProvider(HolidayCalculatorModelProvider aHolidayModelProvider) {
		Objects.requireNonNull(aHolidayModelProvider);
		_holidayModelProvider = aHolidayModelProvider;
		aHolidayModelProvider.addListener(new StatementsChangedListener());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LoadStatus getLoadStatus() {
		return _holidayModelProvider.getLoadStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLoadListener(ILoadListener aLoadListener) {
		_holidayModelProvider.addLoadListener(aLoadListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLoadListener(ILoadListener aLoadListener) {
		_holidayModelProvider.removeLoadListener(aLoadListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Statement<?>> getStatements() {
		HolidayCalculatorModel model = _holidayModelProvider.getModel();

		return model.getCurrentUserStatements();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addStatementsChangedListener(IStatementsChangedListener aListener) {
		Objects.requireNonNull(aListener);
		_listeners.add(aListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeStatementsChangedListener(IStatementsChangedListener aListener) {
		Objects.requireNonNull(aListener);
		_listeners.remove(aListener);
	}

	private class StatementsChangedListener implements IHolidayCalculatorModelListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handleEvent(HolidayCalculatorEvent aEvent) {
			for (IStatementsChangedListener listener : _listeners) {
				listener.statementsChanged();
			}
		}
	}

}
