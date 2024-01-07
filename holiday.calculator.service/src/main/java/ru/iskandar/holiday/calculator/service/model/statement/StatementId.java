package ru.iskandar.holiday.calculator.service.model.statement;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Идентификатор заявления.
 */
@RequiredArgsConstructor
@ToString(of = "_uuid")
@EqualsAndHashCode(of = "_uuid")
@XmlRootElement(name = "StatementId")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatementId implements Serializable {

    /** Идентификатор для сериализации */
    private static final long serialVersionUID = -2897538723997072767L;

    /** UUID заявления */
    @NonNull
    @XmlElement(name = "uuid")
    private final UUID _uuid;

    public static StatementId from(@NonNull UUID aUUID) {
        return new StatementId(aUUID);
    }

    /**
     * Возвращает UUID.
     *
     * @return UUID
     */
    public UUID getUUID() {
        return _uuid;
    }

}
