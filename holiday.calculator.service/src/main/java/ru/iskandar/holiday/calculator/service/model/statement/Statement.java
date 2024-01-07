package ru.iskandar.holiday.calculator.service.model.statement;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;
import ru.iskandar.holiday.calculator.user.service.api.User;

/**
 * Заявление
 */
@Getter
@EqualsAndHashCode(of = "_id")
@Accessors(prefix = "_")
@ToString(of = "_id")
@XmlAccessorType(XmlAccessType.NONE)
public abstract class Statement<E extends StatementEntry> implements Serializable {

    /** Идентификатор для сериализации */
    private static final long serialVersionUID = -1327944012746268176L;

    /** Идентификатор заявления */
    @NonNull
    private final StatementId _id;

    /** Содержимое заявления */
    @NonNull
    @XmlElement(name = "entry")
    private final E _entry;

    @XmlElement(name = "UUID")
    private UUID _uUID;

    /**
     * @param aId
     * @param aEntry
     */
    public Statement(@NonNull StatementId aId, @NonNull E aEntry) {
        _id = aId;
        _entry = aEntry;
        // для WEB API
        _uUID = aId.getUUID();
    }

    /**
     * @return the status
     */
    public StatementStatus getStatus() {
        return _entry.getStatus();
    }

    /**
     * @param aStatus the status to set
     */
    public void setStatus(StatementStatus aStatus) {
        Objects.requireNonNull(aStatus);
        _entry.setStatus(aStatus);
    }

    /**
     * @return the author
     */
    public User getAuthor() {
        return _entry.getAuthor();
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return _entry.getCreateDate();
    }

    /**
     * @param aCreateDate the createDate to set
     */
    public void setCreateDate(Date aCreateDate) {
        _entry.setCreateDate(aCreateDate);
    }

    /**
     * @return the consider
     */
    public User getConsider() {
        return _entry.getConsider();
    }

    /**
     * @param aConsider the consider to set
     */
    public void setConsider(User aConsider) {
        _entry.setConsider(aConsider);
    }

    /**
     * @return the considerDate
     */
    public Date getConsiderDate() {
        return _entry.getConsiderDate();
    }

    /**
     * @param aConsiderDate the considerDate to set
     */
    public void setConsiderDate(Date aConsiderDate) {
        _entry.setConsiderDate(aConsiderDate);
    }

    public final StatementType getStatementType() {
        return _entry.getStatementType();
    }

    public String getDescription() {
        String createDate = DateUtils.format(getCreateDate(), DateUtils.DATE_FORMAT);
        return String.format("%s от %s", getStatementType().getDescription(), createDate);
    }

}
