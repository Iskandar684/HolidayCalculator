package ru.iskandar.holiday.calculator.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.model.statement.StatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.StatementType;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Событие изменения содержания заявления
 */
@Getter
@Accessors(prefix = "_")
@RequiredArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class StatementContentChangedEvent extends HolidayCalculatorEvent {

    /** Идентификатор для сериализации */
    private static final long serialVersionUID = 1471930739712522667L;

    @XmlElement(name = "id")
    private final String _id = "StatementContentChangedEvent";

    @XmlElement(name = "name")
    private final String _name = "изменение содержания заявления";

    @NonNull
    private final StatementEntry _statementEntry;

    @XmlElement(name = "description")
    @Override
    public String getDescription() {
        StatementType type = _statementEntry.getStatementType();
        String createDate = DateUtils.format(_statementEntry.getCreateDate());
        return String.format("%s от %s было изменено.", type.getDescription(), createDate);
    }
}
