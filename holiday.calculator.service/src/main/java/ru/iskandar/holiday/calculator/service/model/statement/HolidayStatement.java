package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Заявление на отгул
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class HolidayStatement extends Statement<HolidayStatementEntry> {

	/**
	 * Идентификатор для сериализации
	 */
	private static final long serialVersionUID = -4442838042007056450L;

	/**
	 * Конструктор
	 *
	 * @param aId
	 *            идентификатор
	 */
	public HolidayStatement(StatementId aId, HolidayStatementEntry aEntry) {
		super(aId, aEntry);
	}

	/**
	 * @return the days
	 */
	public Set<Date> getDays() {
		return getEntry().getDays();
	}

}
