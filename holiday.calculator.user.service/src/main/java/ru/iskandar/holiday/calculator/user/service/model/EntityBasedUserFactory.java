package ru.iskandar.holiday.calculator.user.service.model;

import java.util.Date;
import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.iskandar.holiday.calculator.user.service.entity.UserEntity;

/**
 * Фабрика создания описания пользователя на основе сущности пользователя
 */
@RequiredArgsConstructor
public class EntityBasedUserFactory extends UserFactory {

    /** Сущность пользователя */
    @NonNull
    private final UserEntity _userEntity;

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getUUID() {
        return _userEntity.getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFirstName() {
        return _userEntity.getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLastName() {
        return _userEntity.getLastName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPatronymic() {
        return _userEntity.getPatronymic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Date getEmploymentDate() {
        return _userEntity.getEmploymentDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLogin() {
        return _userEntity.getLogin();
    }

    @Override
    protected String getNote() {
        return _userEntity.getNote();
    }

}
