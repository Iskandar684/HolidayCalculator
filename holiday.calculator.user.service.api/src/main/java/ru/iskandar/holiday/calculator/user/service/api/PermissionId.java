package ru.iskandar.holiday.calculator.user.service.api;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Идентификатор полномочия
 */
@Accessors(prefix = "_")
@EqualsAndHashCode(of = "_id")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionId implements Serializable {

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = 4936714256790646532L;

	/** Идентификатор полномочия */
	@Getter
	private final String _id;

	/**
	 * Создает идентификатор полномочия по UUID
	 *
	 * @param aPermissionId строковый идентификатор полномочия
	 * @return идентификатор полномочия
	 * @throws NullPointerException если aPermissionId == {@code null}
	 */
	public static PermissionId from(@NonNull String aPermissionId) {
		return new PermissionId(aPermissionId);
	}

	/**
	 * Создает идентификатор полномочия по UUID
	 *
	 * @param aPermissionId строковый идентификатор полномочия
	 * @return идентификатор полномочия
	 * @throws NullPointerException если aPermissionId == {@code null}
	 */
	// для REST
	public static PermissionId fromString(String aPermissionId) {
		return from(aPermissionId);
	}

}
