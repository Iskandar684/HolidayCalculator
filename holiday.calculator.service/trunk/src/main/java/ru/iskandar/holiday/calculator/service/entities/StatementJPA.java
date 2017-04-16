package ru.iskandar.holiday.calculator.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Сущность заявления
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_statement")
public class StatementJPA {

	@Id
	@GeneratedValue
	@Column(name = "suid")
	private long _suid;

	@Column(name = "text")
	private String _text;

	/**
	 * @return the suid
	 */
	public long getSuid() {
		return _suid;
	}

	/**
	 * @param aSuid
	 *            the suid to set
	 */
	public void setSuid(long aSuid) {
		_suid = aSuid;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return _text;
	}

	/**
	 * @param aText
	 *            the text to set
	 */
	public void setText(String aText) {
		_text = aText;
	}

}
