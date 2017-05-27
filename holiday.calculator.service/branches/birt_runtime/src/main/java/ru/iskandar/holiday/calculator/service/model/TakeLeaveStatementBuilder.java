package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;

/**
 * Формирователь заявления на отпуск
 */
public class TakeLeaveStatementBuilder implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -6563870195288950500L;

	/** Дни на отгул */
	private final Set<Date> _days = new HashSet<>();

	/** Модель */
	private final HolidayCalculatorModel _model;

	/**
	 * Конструктор
	 */
	TakeLeaveStatementBuilder(HolidayCalculatorModel aModel) {
		Objects.requireNonNull(aModel);
		_model = aModel;
	}

	/**
	 * Подает заявление на отпуск
	 *
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public void sendLeaveStatement() throws StatementAlreadySendedException {
		LeaveStatementEntry statement = new LeaveStatementEntry(_model.getCurrentUser(), _days);
		_model.sendLeaveStatement(statement);
	}

	/**
	 * Возвращает возможность подачи заявления на отпуск
	 *
	 * @return {@code true}, если подача заявления на отпуск разрешено
	 */
	public boolean canSendLeaveStatement() {
		return true;
	}

	public void addDate(Date aDate) {
		_days.add(aDate);
	}

	public void removeDate(Date aDate) {
		_days.remove(aDate);
	}

	public void clearDates() {
		_days.clear();
	}

	public void setDays(Collection<Date> aDates) {
		Objects.requireNonNull(aDates);
		if (aDates.contains(null)) {
			throw new IllegalArgumentException("Список дат содержит null");
		}
		_days.clear();
		_days.addAll(aDates);
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return Collections.unmodifiableSet(_days);
	}

}
