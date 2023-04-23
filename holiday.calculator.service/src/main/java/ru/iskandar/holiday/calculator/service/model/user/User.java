package ru.iskandar.holiday.calculator.service.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Пользователь
 */
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    /**
     * Уникальный идентификатор класса для сериализации
     */
    private static final long serialVersionUID = 7332495096773696543L;

    /** Имя */
    @XmlElement(name = "firstName")
    private final String _firstName;

    /** Фамилия */
    @XmlElement(name = "lastName")
    private final String _lastName;

    /** Отчество */
    @XmlElement(name = "patronymic")
    private final String _patronymic;

    /** Идентификатор */
    @XmlElement(name = "uuid")
    private final UUID _uuid;

    /** Дата приема на работу */
    @XmlElement(name = "employmentDate")
    private final Date _employmentDate;

    /** Логин */
    @XmlElement(name = "login")
    private final String _login;

    /** Примечание */
    @XmlElement(name = "note")
    private String _note;

    /**
     * Конструктор
     */
    protected User(UUID aUUID, String aLastName, String aFirstName, String aPatronymic,
            Date aEmploymentDate, String aLogin, String aNote) {
        _uuid = Objects.requireNonNull(aUUID);
        _lastName = Objects.requireNonNull(aLastName);
        _firstName = Objects.requireNonNull(aFirstName);
        _patronymic = Objects.requireNonNull(aPatronymic);
        _employmentDate = Objects.requireNonNull(aEmploymentDate);
        _login = Objects.requireNonNull(aLogin);
        _note = aNote;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return _firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return _lastName;
    }

    /**
     * @return the patronymic
     */
    public String getPatronymic() {
        return _patronymic;
    }

    public String getFIO() {
        return String.format("%s %s %s", _lastName, _firstName, _patronymic);
    }

    /**
     * @return the employmentDate
     */
    public Date getEmploymentDate() {
        return _employmentDate;
    }

    /**
     * @return the uuid
     */
    public UserId getId() {
        return UserId.from(_uuid);
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return _login;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String aNote) {
        // FIXME убрать публичный сеттер
        _note = aNote;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((_uuid == null) ? 0 : _uuid.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
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
        User other = (User) aObj;
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
