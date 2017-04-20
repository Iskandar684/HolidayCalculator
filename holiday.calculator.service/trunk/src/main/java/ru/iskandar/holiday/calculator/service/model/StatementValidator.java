package ru.iskandar.holiday.calculator.service.model;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Валидатор правильности заполнения заявления
 */
public class StatementValidator {

	/**
	 * Проверяет коррекность заполнения заявления
	 *
	 * @param aStatement
	 *            заявление
	 * @return {@code true}, если заявление заполнено корректно; или причина,
	 *         если некорректно
	 */
	public String validateStatement(Statement aStatement) {
		Objects.requireNonNull(aStatement);
		if (aStatement.getAuthor() == null) {
			return "Не указан автор заявления";
		}
		StatementType type = aStatement.getStatementType();
		if (type == null) {
			return "Не указан тип заявления";
		}
		if (aStatement.getId() == null) {
			return "Не указан ID заявления";
		}
		if (aStatement.getCreateDate() == null) {
			return "Не указана дата создания заявления";
		}
		StatementStatus status = aStatement.getStatus();
		if (status == null) {
			return "Не указан статус заявления";
		}

		switch (status) {
		case APPROVE:
		case REJECTED:
			if (aStatement.getConsider() == null) {
				return "Не указан пользователь, рассмотревший заявление";
			}
			if (aStatement.getConsiderDate() == null) {
				return "Не указана дата рассмотрения заявления";
			}
			break;

		default:
			break;
		}

		switch (type) {
		case HOLIDAY_STATEMENT:
			HolidayStatement holidayStatement = (HolidayStatement) aStatement;
			if ((holidayStatement.getDays() == null) || holidayStatement.getDays().isEmpty()) {
				return "Не указаны дни (день) на отгул";
			}
			break;

		case RECALL_STATEMENT:
			RecallStatement recallStatement = (RecallStatement) aStatement;
			if ((recallStatement.getRecallDates() == null) || recallStatement.getRecallDates().isEmpty()) {
				return "Не указаны дни (день) на отзыв";
			}
			break;

		case LEAVE_STATEMENT:
			LeaveStatement leaveStatement = (LeaveStatement) aStatement;
			Set<Date> leaveDates = leaveStatement.getLeaveDates();
			if ((leaveDates == null) || leaveDates.isEmpty()) {
				return "Не указаны дни отпуска";
			}
			// TODO проверить, что один и тот же день не указан несколько раз
			// (например, с другими часами, минутами)
			break;

		default:
			break;
		}
		return null;
	}

	/**
	 * Проверяет коррекность заполнения заявления
	 *
	 * @param aStatement
	 *            заявление
	 * @throws InvalidStatementException
	 *             если заявление заполнено некорректно
	 */
	public void checkStatement(Statement aStatement) {
		Objects.requireNonNull(aStatement);
		String cause = validateStatement(aStatement);
		if (cause != null) {
			throw new InvalidStatementException(
					String.format("Заявление '%s' заполнено неправильно: %s", aStatement, cause));
		}
	}

}
