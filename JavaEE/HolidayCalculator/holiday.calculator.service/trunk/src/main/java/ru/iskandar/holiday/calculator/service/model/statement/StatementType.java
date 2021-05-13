package ru.iskandar.holiday.calculator.service.model.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Тип заявления
 */
@Getter
@Accessors(prefix = "_")
@RequiredArgsConstructor
public enum StatementType {

    /** Заявление на отгул */
    HOLIDAY_STATEMENT("Заявление на отгул"),

    /** Заявление на отзыв */
    RECALL_STATEMENT("Заявление на отзыв"),

    /** Заявление на отпуск */
    LEAVE_STATEMENT("Заявление на отпуск");

    private final String _description;
}
