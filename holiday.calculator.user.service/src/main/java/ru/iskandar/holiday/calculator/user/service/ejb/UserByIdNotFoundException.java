package ru.iskandar.holiday.calculator.user.service.ejb;

/**
 * Исключение, прокидываемое, если пользователь по идентификатору не найден.
 */
public class UserByIdNotFoundException extends RuntimeException {

    /**
     * Идентификатор для сериализации
     */
    private static final long serialVersionUID = 3175712490342357128L;

    /**
     * Конструктор.
     *
     * @param aMessage сообщение
     */
    public UserByIdNotFoundException(String aMessage) {
        super(aMessage);
    }

    /**
     * Конструктор.
     *
     * @param aMessage сообщение
     * @param aCause причина
     */
    public UserByIdNotFoundException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

}
