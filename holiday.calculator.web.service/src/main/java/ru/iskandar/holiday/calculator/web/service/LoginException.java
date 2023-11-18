package ru.iskandar.holiday.calculator.web.service;


/**
 * Исключение ошибки входа в систему.
 */
public class LoginException extends RuntimeException {

    /** Идентификатор для сериализации */
    private static final long serialVersionUID = -8570678989970293781L;

    /**
     * Конструктор.
     */
    public LoginException(String aMessage, Exception aCause) {
        super(aMessage, aCause);
    }

}
