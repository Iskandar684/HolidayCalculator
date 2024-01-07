package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Заявление на отгул
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class HolidayStatementEntry extends StatementEntry {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = 5925653285567737310L;

	/** Дата отгула */
	private final Set<Date> _days;

	/**
	 * Конструктор
	 *
	 * @param aUUID
	 *            идентификатор
	 */
	public HolidayStatementEntry(Set<Date> aDays, User aAuthor) {
		super(aAuthor);
		Objects.requireNonNull(aDays);
		_days = new HashSet<>(aDays);
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return Collections.unmodifiableSet(_days);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatementType getStatementType() {
		return StatementType.HOLIDAY_STATEMENT;
	}

}
