package ru.iskandar.holiday.calculator.user.service.api;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Описание для создания нового пользователя
 */
@Getter
@Setter
@Accessors(prefix = "_")
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class NewUserEntry implements Serializable {

    /**
     * Идентификатор для сериализации
     */
    private static final long serialVersionUID = 7550226147547113824L;

    /** Имя */
    @XmlElement(name="firstName")
    private String _firstName;

    /** Фамилия */
    @XmlElement(name="lastName")
    private String _lastName;

    /** Отчество */
    @XmlElement(name="patronymic")
    private String _patronymic;

    /** Дата приема на работу */
   // @XmlElement("employmentDate")
    private Date _employmentDate = new Date();

    /** Логин */
    @XmlElement(name="login")
    private String _login;

    /** Пароль */
    @XmlElement(name="password")
    private String _password;

    /** Примечание */
    @XmlElement(name="note")
    private String _note;

    /**
     * Конструктор
     */
    public NewUserEntry(String aLastName, String aFirstName, String aPatronymic,
            Date aEmploymentDate, String aLogin) {
        Objects.requireNonNull(aLastName);
        Objects.requireNonNull(aFirstName);
        Objects.requireNonNull(aPatronymic);
        Objects.requireNonNull(aEmploymentDate);
        Objects.requireNonNull(aLogin);
        _firstName = aFirstName;
        _lastName = aLastName;
        _patronymic = aPatronymic;
        _employmentDate = aEmploymentDate;
        _login = aLogin;
    }

    public boolean isEmpty() {
        if ((getLogin() == null) || getLogin().isEmpty()) {
                return true;
        }
        if ((getFirstName() == null) || getFirstName().isEmpty()) {
                return true;
        }
        if ((getLastName() == null) || getLastName().isEmpty()) {
                return true;
        }
        if ((getPatronymic() == null) || getPatronymic().isEmpty()) {
                return true;
        }
        if (getEmploymentDate() == null) {
                return true;
        }

        if ((getPassword() == null) || getPassword().isEmpty()) {
                return true;
        }
        return false;
}

}
