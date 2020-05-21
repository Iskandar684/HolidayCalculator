package ru.iskandar.holiday.calculator.web.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement
@Getter
@Setter
@Accessors(prefix = "_")
@Builder
public class ErrorResponse {

    @XmlAttribute(name = "code")
    private int _code;

    @XmlAttribute(name = "message")
    private String _message;

    @XmlAttribute(name = "description")
    private String _description;

}
