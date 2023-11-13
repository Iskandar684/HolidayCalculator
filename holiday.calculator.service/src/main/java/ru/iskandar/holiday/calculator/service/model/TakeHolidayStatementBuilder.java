package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ru.iskandar.holiday.calculator.service.model.document.DocumentPreviewException;
import ru.iskandar.holiday.calculator.service.model.document.StatementDocument;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementType;

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
		notifyContentChanged();
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
		HolidayStatementEntry statement = build();
		_model.sendHolidayStatement(statement);
	}

	/**
	 * Возвращает описание заявления на отгул
	 *
	 * @return описание заявления на отгул
	 */
	private HolidayStatementEntry build() {
		HolidayStatementEntry statement = new HolidayStatementEntry(_days, _model.getCurrentUser());
		return statement;
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
		notifyContentChanged();
	}

	public void addDate(Date aDate) {
		_days.add(aDate);
		notifyContentChanged();
	}

	public void removeDate(Date aDate) {
		_days.remove(aDate);
		notifyContentChanged();
	}

	public void clearDates() {
		_days.clear();
		notifyContentChanged();
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return Collections.unmodifiableSet(_days);
	}

	/**
	 * Формирует документ заявления на отгул
	 *
	 * @param aEntry
	 *            содержание заявления на отгул
	 * @return документ заявления
	 * @throws DocumentPreviewException
	 *             если не удалось сформировать документ
	 */
	public StatementDocument preview() throws DocumentPreviewException {
		return _model.preview(build());
	}

	private void notifyContentChanged() {
		_model.fireEvent(new StatementContentChangedEvent(build()));
	}

}