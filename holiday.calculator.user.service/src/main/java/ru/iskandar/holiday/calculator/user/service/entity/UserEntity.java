package ru.iskandar.holiday.calculator.user.service.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import ru.iskandar.holiday.calculator.user.service.api.UserConstants;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сущность пользователя.
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_user")
public class UserEntity implements Serializable {

    /**
     * Идентфикатор для сериализации
     */
    private static final long serialVersionUID = -7202845772535656629L;

    /** Глобальный идентификатор */
    private UUID _uuid;

    /** Имя */
    private String _firstName;

    /** Фамилия */
    private String _lastName;

    /** Отчество */
    private String _patronymic;

    /** Логин пользователя */
    private String _login;

    /** Дата приема на работу */
    private Date _employmentDate;

    /** Примечание */
    private String _note;

    /**
     * Возвращает идентификатор пользователя.
     *
     * @return идентификатор пользователя
     */
    @Id
    @GeneratedValue
    @Column(name = "uuid")
    public UUID getUuid() {
        return _uuid;
    }

    /**
     * Устанавливает идентификатор пользователя.
     *
     * @param aUuid идентификатор пользователя
     */
    public void setUuid(UUID aUuid) {
        _uuid = aUuid;
    }

    /**
     * Возвращает имя.
     *
     * @return имя
     */
    @Column(name = "firstname")
    @NotNull(message = "Не указано имя")
    public String getFirstName() {
        return _firstName;
    }

    /**
     * Устанавливает имя.
     *
     * @param aFirstName имя
     */
    public void setFirstName(String aFirstName) {
        _firstName = aFirstName;
    }

    /**
     * Возвращает фамилию.
     *
     * @return фамилию
     */
    @Column(name = "lastname")
    @NotNull(message = "Не указана фамилия")
    public String getLastName() {
        return _lastName;
    }

    /**
     * Устанавливает фамилию.
     *
     * @param aLastName фамилия
     */
    public void setLastName(String aLastName) {
        _lastName = aLastName;
    }

    /**
     * Возвращает отчество.
     *
     * @return отчество
     */
    @Column(name = "patronymic")
    @NotNull(message = "Не указано отчество")
    public String getPatronymic() {
        return _patronymic;
    }

    /**
     * Устанавливает отчество.
     *
     * @param aPatronymic отчество
     */
    public void setPatronymic(String aPatronymic) {
        _patronymic = aPatronymic;
    }

    /**
     * Возвращает логин.
     *
     * @return логин
     */
    @Column(name = "login", unique = true)
    @NotNull(message = "Не указан логин")
    public String getLogin() {
        return _login;
    }

    /**
     * Устанавливает логин.
     *
     * @param aLogin логин
     */
    public void setLogin(String aLogin) {
        _login = aLogin;
    }

    /**
     * Возвращает дату приема на работу.
     *
     * @return дату приема на работу
     */
    @Column(name = "employmentdate")
    @NotNull(message = "Не указан логин")
    public Date getEmploymentDate() {
        return _employmentDate;
    }

    /**
     * Устанавливает дату приема на работу.
     *
     * @param aEmploymentDate дата приема на работу
     */
    public void setEmploymentDate(Date aEmploymentDate) {
        _employmentDate = aEmploymentDate;
    }

    /**
     * Возвращает примечание.
     *
     * @return примечание
     */
    @Column(name = "note", length = UserConstants.NOTE_LENGHT)
    public String getNote() {
        return _note;
    }

    /**
     * Устанавливает примечание.
     *
     * @param aNote примечание
     */
    public void setNote(String aNote) {
        _note = aNote;
    }

    /**
     * Возвращает идентификатор.
     *
     * @return идентификатор
     */
    @Transient
    public UserId getId() {
        return (_uuid == null) ? null : UserId.fromString(_uuid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((_uuid == null) ? 0 : _uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object aObj) {
        if (this == aObj) {
            return true;
        }
        if (aObj == null) {
            return false;
        }
        if (getClass() != aObj.getClass()) {
            return false;
        }
        UserEntity other = (UserEntity) aObj;
        if (_uuid == null) {
            if (other._uuid != null) {
                return false;
            }
        } else if (!_uuid.equals(other._uuid)) {
            return false;
        }
        return true;
    }

}
