package ru.iskandar.holiday.calculator.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementType;
import ru.iskandar.holiday.calculator.service.utils.DateUtils;

/**
 * Событие о подачи заявления
 */
@Getter
@Accessors(prefix = "_")
@RequiredArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class StatementSendedEvent extends HolidayCalculatorEvent {

    /** Идентификатор для сериализации */
    private static final long serialVersionUID = -3835067673271539642L;

    @XmlElement(name = "id")
    private final String _id = "StatementSendedEvent";

    /** Заявление, связанное с событием */
    @NonNull
    private final Statement<?> _affectedStatement;

    @XmlElement(name = "description")
    @Override
    public String getDescription() {
        StatementType type = _affectedStatement.getStatementType();
        String createDate = DateUtils.format(_affectedStatement.getCreateDate());
        return String.format("Создано %s от %s сотрудником %s.",
                type.getDescription().toLowerCase(), createDate,
                _affectedStatement.getAuthor().getFIO());
    }

}
