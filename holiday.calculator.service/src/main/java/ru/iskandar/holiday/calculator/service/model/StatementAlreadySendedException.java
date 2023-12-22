package ru.iskandar.holiday.calculator.service.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import ru.iskandar.holiday.calculator.service.ejb.HolidayCalculatorException;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementEntry;

/**
 * Исключение для случая, когда заявление уже было отправлено
 */
@Accessors(prefix = "_")
public class StatementAlreadySendedException extends HolidayCalculatorException {

    /**
     * Идентификатор
     */
    private static final long serialVersionUID = -2540748294773759275L;

    /** Отправляемое заявление */
    @Getter
    private final StatementEntry _sendingStatement;

    /** Ранее отправленное заявление */
    @Getter
    private final Statement<?> _earlySendenStatement;

    /**
     * @param aMessage
     */
    public StatementAlreadySendedException(StatementEntry aSendingStatement,
            Statement<?> aEarlySendenStatement) {
        super("Заявление на указанную дату(даты) уже было подано ранее.");
        _sendingStatement = aSendingStatement;
        _earlySendenStatement = aEarlySendenStatement;
    }

}
