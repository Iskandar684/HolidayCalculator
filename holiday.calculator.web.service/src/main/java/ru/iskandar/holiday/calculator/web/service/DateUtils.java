package ru.iskandar.holiday.calculator.web.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 *
 */
@UtilityClass
public class DateUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public LocalDate toLocalDate(@NonNull Date aDate) {
        return aDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
