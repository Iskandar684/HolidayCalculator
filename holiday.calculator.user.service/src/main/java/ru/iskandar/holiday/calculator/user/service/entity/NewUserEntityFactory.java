package ru.iskandar.holiday.calculator.user.service.entity;

import java.util.Date;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.iskandar.holiday.calculator.user.service.api.NewUserEntry;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Фабрика сущности нового пользователя.
 */
@RequiredArgsConstructor
public class NewUserEntityFactory extends UserEntityFactory {

    @NonNull
    private final NewUserEntry _user;

    @Override
    protected UserId getId() {
        return null;
    }

    @Override
    protected String getFirstName() {
        return _user.getFirstName();
    }

    @Override
    protected String getLastName() {
        return _user.getLastName();
    }

    @Override
    protected String getPatronymic() {
        return _user.getPatronymic();
    }

    @Override
    protected String getLogin() {
        return _user.getLogin();
    }

    @Override
    protected Date getEmploymentDate() {
        return _user.getEmploymentDate();
    }

    @Override
    protected String getNote() {
        return _user.getNote();
    }

}
