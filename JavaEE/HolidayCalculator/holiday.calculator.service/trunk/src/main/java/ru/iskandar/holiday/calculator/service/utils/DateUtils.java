package ru.iskandar.holiday.calculator.service.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Утилиты для даты
 */
@UtilityClass
public class DateUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String DATE_FORMAT = "dd.MM.yyyy";

    /**
     * Возвращает признак того, что коллекции содержат пересекающиеся даты
     *
     * @param aDates1 коллекция дат 1
     * @param aDates2 коллекция дат 2
     * @return {@code true}, если в обоих коллекциях есть даты, с одинаковыми "год", "месяц", "день"
     */
    public static boolean hasIntersectionDays(Collection<Date> aDates1, Collection<Date> aDates2) {
        Objects.requireNonNull(aDates1);
        Objects.requireNonNull(aDates2);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        for (Date date1 : aDates1) {
            cal1.setTime(date1);
            int day1 = cal1.get(Calendar.DATE);
            int month1 = cal1.get(Calendar.MONTH);
            int year1 = cal1.get(Calendar.YEAR);

            for (Date date2 : aDates2) {
                cal2.setTime(date2);
                int day2 = cal2.get(Calendar.DATE);
                int month2 = cal2.get(Calendar.MONTH);
                int year2 = cal2.get(Calendar.YEAR);

                boolean daysEqual = day1 == day2;
                boolean monthEqual = month1 == month2;
                boolean yearsEqual = year1 == year2;
                if (daysEqual && monthEqual && yearsEqual) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String toString(Collection<Date> aDates) {
        if (aDates.isEmpty()) {
            return "на ____ ";
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        if (aDates.size() == 1) {
            String date = dateFormatter.format(aDates.iterator().next());
            return String.format("на %s", date);
        }

        StringBuilder builder = new StringBuilder();
        List<Date> sortedDates = new ArrayList<>(aDates);

        Collections.sort(sortedDates);

        if (isNeighbor(sortedDates)) {
            Date first = sortedDates.get(0);
            Date end = sortedDates.get(sortedDates.size() - 1);
            builder.append("с ");
            builder.append(dateFormatter.format(first));
            builder.append(" по ");
            builder.append(dateFormatter.format(end));
        } else {
            builder.append("на ");
            Iterator<Date> it = sortedDates.iterator();
            while (it.hasNext()) {
                builder.append(dateFormatter.format(it.next()));
                if (it.hasNext()) {
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }

    private static boolean isNeighbor(Collection<Date> aDates) {
        if (aDates.size() < 1) {
            return false;
        }
        Date previous = null;
        Calendar cal = Calendar.getInstance();
        for (Date date : aDates) {
            cal.setTime(date);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
            if ((previous != null) && !equalDateMonthYear(cal.getTime(), previous)) {
                return false;
            }
            previous = date;
        }

        return true;
    }

    private static boolean equalDateMonthYear(Date aDate1, Date aDate2) {
        Objects.requireNonNull(aDate1);
        Objects.requireNonNull(aDate2);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(aDate1);
        int day1 = cal1.get(Calendar.DATE);
        int month1 = cal1.get(Calendar.MONTH);
        int year1 = cal1.get(Calendar.YEAR);

        cal2.setTime(aDate2);
        int day2 = cal2.get(Calendar.DATE);
        int month2 = cal2.get(Calendar.MONTH);
        int year2 = cal2.get(Calendar.YEAR);
        return (day1 == day2) && (month1 == month2) && (year1 == year2);
    }

    public String format(@NonNull Date aDate) {
        return format(aDate, DATE_FORMAT);
    }

    public String format(@NonNull Date aDate, @NonNull String aFormat) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(aFormat);
        return dateFormatter.format(aDate);
    }

}
