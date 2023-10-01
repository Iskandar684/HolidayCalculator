package ru.iskandar.holiday.calculator.user.service.ejb;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Аннотация web-метода. Служит для получения описания web-метода.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface HCWebMethod {

    /**
     * Возвращает текст ошибки выполнения метода.
     *
     * @return текст ошибки выполнения метода
     */
    String errMess();

}
