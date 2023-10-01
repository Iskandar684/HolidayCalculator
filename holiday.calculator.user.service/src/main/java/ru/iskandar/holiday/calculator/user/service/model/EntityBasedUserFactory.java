package ru.iskandar.holiday.calculator.user.service.model;

import java.util.Date;
import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.iskandar.holiday.calculator.user.service.entity.UserEntity;

/**
 * Фабрика создания описания пользователя на основе сущности пользователя.
 */
@RequiredArgsConstructor
public class EntityBasedUserFactory extends UserFactory {

    /** Сущность пользователя */
    @NonNull
    private final UserEntity _userEntity;

    @Override
    protected UUID getUUID() {
        return _userEntity.getUuid();
    }

    @Override
    protected String getFirstName() {
        return _userEntity.getFirstName();
    }

    @Override
    protected String getLastName() {
        return _userEntity.getLastName();
    }

    @Override
    protected String getPatronymic() {
        return _userEntity.getPatronymic();
    }

    @Override
    protected Date getEmploymentDate() {
        return _userEntity.getEmploymentDate();
    }

    @Override
    protected String getLogin() {
        return _userEntity.getLogin();
    }

    @Override
    protected String getNote() {
        return _userEntity.getNote();
    }

}
