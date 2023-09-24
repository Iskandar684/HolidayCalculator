package ru.iskandar.holiday.calculator.service.entities;

import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Интерфейс сущности заявлений.
 */
public interface IStatementEntity {

    UserId getAuthorId();

    UserId getConsiderId();

}
