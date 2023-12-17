package ru.iskandar.holiday.calculator.web.service;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Запрос на создание заявления на отгул.
 */
@Setter
@Getter
@Accessors(prefix = "_")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewHolidayStatementRequest {

    /** Даты */
    @XmlElement(name = "dates", required = true)
    private Date[] _dates;

}
