package ru.iskandar.holiday.calculator.service.model;

import java.util.Objects;

/**
 * Полномочия
 */
public enum Permissions {

	/** Рассмотрение заявлений */
	CONSIDER("consider");

	private String _id;

	private Permissions(String aId) {
		Objects.requireNonNull(aId);
		_id = aId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return _id;
	}

}
