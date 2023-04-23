package ru.iskandar.holiday.calculator.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Инициатор события
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@Accessors(prefix = "_")
@EqualsAndHashCode(of = {"_clientId", "_user"})
@RequiredArgsConstructor
public class EventInitiator {

    @NonNull
    @XmlElement(name = "client")
    private final ClientId _clientId;

    @NonNull
    @XmlElement(name = "user")
    private final User _user;

    public static EventInitiator create(ClientId aClientId, User aUser) {
        return new EventInitiator(aClientId, aUser);
    }

}
