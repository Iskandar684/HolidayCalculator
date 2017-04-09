package ru.iskandar.holiday.calculator.service.model;

public class HolidayCalculatorListenerAdapter implements IHolidayCalculatorModelListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void handleEvent(HolidayCalculatorEvent aEvent) {
		if (aEvent instanceof StatementSendedEvent) {
			statementSended((StatementSendedEvent) aEvent);
		} else if (aEvent instanceof StatementConsideredEvent) {
			statementConsidered((StatementConsideredEvent) aEvent);
		}
	}

	protected void statementSended(StatementSendedEvent aEvent) {
		switch (aEvent.getAffectedStatement().getStatementType()) {
		case HOLIDAY_STATEMENT:
			holidayStatementSended(aEvent);
			break;
		case LEAVE_STATEMENT:
			leaveStatementSended(aEvent);
			break;
		case RECALL_STATEMENT:
			recallStatementSended(aEvent);
			break;
		default:
			break;
		}
	}

	protected void recallStatementSended(StatementSendedEvent aEvent) {
	}

	protected void holidayStatementSended(StatementSendedEvent aEvent) {
	}

	protected void leaveStatementSended(StatementSendedEvent aEvent) {
	}

	protected void statementConsidered(StatementConsideredEvent aEvent) {
	}

}
