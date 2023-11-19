package ru.iskandar.holiday.calculator.web.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * Информация об отгулах пользователя.
 */
@Getter
@Builder
@Accessors(prefix = "_")
@EqualsAndHashCode(of = "_userUUID")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserHolidaysInfo implements Serializable {

    /** Идентификатор для сериализации */
    private final static long serialVersionUID = 2706110854742752142L;

    /**
     * Глобальный идентификатор пользователя, к которому относится текущая
     * информация по отгулам
     */
    @NonNull
    @XmlElement(name = "userUUID")
    private final UUID _userUUID;

    /** количество отгулов у текущего пользователя */
    @XmlElement(name = "holidaysQuantity")
    private final int _holidaysQuantity;

    /**
     * Количество исходящих дней отгула. Это количество дней, на которое
     * уменьшется количество общее дней отгула, после того как заявление на
     * отгул будет принято.
     */
    @XmlElement(name = "outgoingHolidaysQuantity")
    private final int _outgoingHolidaysQuantity;

    /**
     * Количество приходящих отгулов. Это количество дней, на которое будет
     * увеличино общее количество отгулов, после того как засчитают отзыв.
     */
    @XmlElement(name = "incomingHolidaysQuantity")
    private final int _incomingHolidaysQuantity;

    /** Количество неиспользованных дней отпуска */
    @XmlElement(name = "leaveCount")
    private final int _leaveCount;

    /**
     * Количество исходящих дней отпуска. Это количество дней, на которое
     * уменьшется количество дней отпуска в этом периоде, после того как
     * заявление на отпуск будет принят.
     */
    @XmlElement(name = "outgoingLeaveCount")
    private final int _outgoingLeaveCount;

    /** Дата начала следующего периода */
    @XmlElement(name = "nextLeaveStartDate")
    private final LocalDate _nextLeaveStartDate;

}
