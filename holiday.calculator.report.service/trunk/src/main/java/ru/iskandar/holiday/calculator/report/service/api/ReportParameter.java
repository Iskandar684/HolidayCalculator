package ru.iskandar.holiday.calculator.report.service.api;

/**
 * Параметр отчета
 */
public class ReportParameter implements IReportParameter {

    /** Идентификатор параметра */
    private final String _id;

    /** Значение параметра */
    private final Object _value;

    /** Отображаемый текст */
    private final String _text;

    /**
     * Конструктор
     * 
     * @param aId идентификатор параметра
     * @param aValue значение параметра
     * @param aDisplayText отображаемый текст
     */
    public ReportParameter(String aId, Object aValue, String aDisplayText) {
        _id = aId;
        _value = aValue;
        _text = aDisplayText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return _id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        return _value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayText() {
        return _text;
    }

}
