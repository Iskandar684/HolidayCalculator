package ru.iskandar.holiday.calculator.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Событие создания пользователя
 */
@Getter
@Accessors(prefix = "_")
@RequiredArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class UserCreatedEvent extends HolidayCalculatorEvent {

    /**
     * Идентификатор для сериализации
     */
    private static final long serialVersionUID = 7395233610755325868L;

    @XmlElement(name = "id")
    private final String _id = "UserCreatedEvent";

    /** Созданный пользователь */
    @NonNull
    private final User _createdUser;

    @XmlElement(name = "description")
    @Override
    public String getDescription() {
        return String.format("На работу принят новый сотрудник: %s.", _createdUser.getFIO());
    }

}
