package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Формирователь заявления на отгул
 */
public class TakeHolidayStatementBuilder implements Serializable {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 3380998355782094502L;

	/** Тип заявления на отгул */
	private HolidayStatementType _statementType = HolidayStatementType.BY_HOLIDAY_DAYS;
	/** Дни на отгул */
	private final Set<Date> _days = new HashSet<>();

	/** Модель */
	private final HolidayCalculatorModel _model;

	/**
	 * Конструктор
	 */
	TakeHolidayStatementBuilder(HolidayCalculatorModel aModel) {
		Objects.requireNonNull(aModel);
		_model = aModel;
	}

	public void setDates(Collection<Date> aDates) {
		Objects.requireNonNull(aDates);
		if (aDates.contains(null)) {
			throw new IllegalArgumentException("Список дат содержит null");
		}
		_days.clear();
		_days.addAll(aDates);
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
	public void sendHolidayStatement() throws StatementAlreadySendedException {
		HolidayStatementEntry statement = new HolidayStatementEntry(_days, _model.getCurrentUser());
		_model.sendHolidayStatement(statement);
	}

	/**
	 * Возвращает возможность подачи заявления на отгул
	 *
	 * @return {@code true}, если подача заявления на отгул разрешено
	 */
	public boolean canSendHolidayStatement() {
		return true;
	}

	/**
	 * @return the statementType
	 */
	public HolidayStatementType getStatementType() {
		return _statementType;
	}

	/**
	 * @param aStatementType
	 *            the statementType to set
	 */
	public void setStatementType(HolidayStatementType aStatementType) {
		_statementType = aStatementType;
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

}
