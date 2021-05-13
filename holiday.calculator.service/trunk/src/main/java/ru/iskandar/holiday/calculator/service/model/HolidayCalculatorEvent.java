package ru.iskandar.holiday.calculator.service.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Событие сервиса учета отгулов
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@Setter
@Accessors(prefix = "_")
public abstract class HolidayCalculatorEvent implements Serializable {

    /** Идентификатор для сериализации */
    private static final long serialVersionUID = 7263240058277798176L;

    /**
     * Идентификатор клиента инициатора события. {@code null}, если инициатором события является не
     * пользователь
     */
    private EventInitiator _initiator;

    public abstract String getId();

    public abstract String getName();

    public abstract String getDescription();

}
