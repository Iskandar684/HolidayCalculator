/**
 *
 */
package ru.iskandar.holiday.calculator.service.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
  *
 */
public class DateUtilsTest {

    /**
     *
     */
    @Test
    public void testHasIntersectionDays1() {
        assertTrue(DateUtils.hasIntersectionDays(Collections.singleton(new Date()),
                Collections.singleton(new Date())));
    }

    /**
     *
     */
    @Test
    public void testHasIntersectionDays2() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);

        assertFalse(DateUtils.hasIntersectionDays(Collections.singleton(cal.getTime()),
                Collections.singleton(new Date())));
    }

    /**
     *
     */
    @Test
    public void testHasIntersectionDays3() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DATE, cal1.get(Calendar.DATE) + 1);

        List<Date> dates1 = new ArrayList<>();
        dates1.add(cal1.getTime());
        dates1.add(new Date());

        assertTrue(DateUtils.hasIntersectionDays(dates1, Collections.singleton(new Date())));
    }

    /**
     *
     */
    @Test
    public void testToStringDatesCollectionForSingleDate() {
        Date date = new Date();
        String actual = DateUtils.toString(Collections.singleton(date));
        String expected = "на " + new SimpleDateFormat("dd.MM.yyyy").format(date);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    public void testToStringDatesCollectionForEmpty() {
        String actual = DateUtils.toString(Collections.emptyList());
        assertEquals("на ____ ", actual);
    }

    /**
     *
     */
    @Test
    public void testToStringDatesCollectionForSeparatedDates() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 3);
        Date date1 = cal.getTime();

        Date date2 = new Date();
        List<Date> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);

        String actual = DateUtils.toString(dates);
        String expected = "на " + new SimpleDateFormat("dd.MM.yyyy").format(date2) + ", "
                + new SimpleDateFormat("dd.MM.yyyy").format(date1);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    public void testToStringDatesCollectionForNeighborDates() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
        Date date1 = cal.getTime();

        cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 2);
        Date date2 = cal.getTime();

        cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 3);
        Date date3 = cal.getTime();

        List<Date> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date3);
        dates.add(date2);

        String actual = DateUtils.toString(dates);
        String expected = "с " + new SimpleDateFormat("dd.MM.yyyy").format(date1) + " по "
                + new SimpleDateFormat("dd.MM.yyyy").format(date3);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatDate() {
        // подготовка
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 20);
        cal.set(Calendar.SECOND, 30);
        Date date = cal.getTime();
        // вызов
        String res = DateUtils.format(date, DateUtils.DATE_TIME_FORMAT);
        // проверка
        assertEquals("2021-05-14T15:20:30", res);
    }

}
