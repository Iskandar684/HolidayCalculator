package ru.iskandar.holiday.calculator.service.ejb;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.persistence.EntityManager;

/**
 *
 */
public final class EntityManagerInjector {

	/**
	 * Конструктор
	 */
	private EntityManagerInjector() {
	}

	public static void inject(Object aObj, EntityManager aEntityManager) {
		Objects.requireNonNull(aObj);
		Objects.requireNonNull(aEntityManager);
		for (Field field : aObj.getClass().getDeclaredFields()) {
			if (EntityManager.class.equals(field.getType())) {
				try {
					field.setAccessible(true);
					field.set(aObj, aEntityManager);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(
							String.format("Ошибка установки в EntityManager %s в поле %s", aEntityManager, field), e);
				}
			}
		}
	}

}
