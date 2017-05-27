/**
 *
 */
package ru.iskandar.holiday.calculator.service.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		assertTrue(DateUtils.hasIntersectionDays(Collections.singleton(new Date()), Collections.singleton(new Date())));
	}

	/**
	 *
	 */
	@Test
	public void testHasIntersectionDays2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);

		assertFalse(
				DateUtils.hasIntersectionDays(Collections.singleton(cal.getTime()), Collections.singleton(new Date())));
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

}
