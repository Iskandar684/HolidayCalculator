package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;

/**
 * Формирователь заявления на отзыв
 */
public class MakeRecallStatementBuilder implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 6191271087530860259L;

	/** Дни на отзыв */
	private final Set<Date> _days = new HashSet<>();

	/** Модель */
	private final HolidayCalculatorModel _model;

	/**
	 * Конструктор
	 */
	MakeRecallStatementBuilder(HolidayCalculatorModel aModel) {
		Objects.requireNonNull(aModel);
		_model = aModel;
		fillDefault();
	}

	public void fillDefault() {
		_days.clear();
		LeaveStatement statement = _model.getLastLeaveStatement();
		if (statement != null) {
			_days.addAll(statement.getLeaveDates());
		}
	}

	/**
	 * Подает заявление на отгул
	 *
	 * @throws StatementAlreadySendedException
	 *             если заявление уже было подано (например, при попытке подать
	 *             второй раз заявление на один и тот же день)
	 * @throws ServiceLookupException
	 *             если не удалось получить сервис учета отгулов
	 */
	public void sendRecallStatement() throws StatementAlreadySendedException {
		RecallStatementEntry statement = new RecallStatementEntry(_days, _model.getCurrentUser());
		_model.sendRecallStatement(statement);
	}

	/**
	 * Возвращает возможность подачи заявления на отзыв
	 *
	 * @return {@code true}, если подача заявления на отгул разрешено
	 */
	public boolean canSendRecallStatement() {
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

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return Collections.unmodifiableSet(_days);
	}

	public void setDays(Collection<Date> aDates) {
		Objects.requireNonNull(aDates);
		if (aDates.contains(null)) {
			throw new IllegalArgumentException("Список дат содержит null");
		}
		_days.clear();
		_days.addAll(aDates);
	}

}
