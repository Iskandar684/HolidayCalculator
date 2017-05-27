package ru.iskandar.holiday.calculator.report.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ru.iskandar.holiday.calculator.report.service.api.IReportInput;

/**
 * Валидатор обязательных входных параметров (данных) отчета
 */
public class RequiredParamsValidator {

	/**
	 * Провалидировать входные данные
	 *
	 * @param aReportInput
	 *            входные данные
	 * @return {@code null}, если данные валидны
	 */
	public static String validate(IReportInput aReportInput) {
		// FIXME реализовать рекурсивный обход
		String err = null;
		if (aReportInput != null) {
			Class<? extends IReportInput> inputClass = IReportInput.class;
			Method[] methods = inputClass.getMethods();
			for (Method md : methods) {
				if (md.isAnnotationPresent(Required.class)) {
					try {
						Object[] params = null;
						Object value = md.invoke(aReportInput, params);
						if (value == null) {
							Required r = md.getAnnotation(Required.class);
							err = String.format("Пустой обязательный параметр", r.name());
							break;
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new IllegalStateException("Ошибка валидации параметров формирования отчета", e);

					}
				}
			}
		} else {
			err = "Пустые входные данные";
		}
		return err;
	}

}
