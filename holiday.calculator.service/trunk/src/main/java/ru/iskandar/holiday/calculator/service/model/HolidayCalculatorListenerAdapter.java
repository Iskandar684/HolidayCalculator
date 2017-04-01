package ru.iskandar.holiday.calculator.service.model;

public class HolidayCalculatorListenerAdapter implements IHolidayCalculatorModelListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void handleEvent(HolidayCalculatorEvent aEvent) {
		if (aEvent instanceof HolidayStatementSendedEvent) {
			holidayStatementSended((HolidayStatementSendedEvent) aEvent);
		} else if (aEvent instanceof StatementConsideredEvent) {
			statementConsidered((StatementConsideredEvent) aEvent);
		}
	}

	protected void holidayStatementSended(HolidayStatementSendedEvent aEvent) {
	}

	protected void statementConsidered(StatementConsideredEvent aEvent) {
	}

}
