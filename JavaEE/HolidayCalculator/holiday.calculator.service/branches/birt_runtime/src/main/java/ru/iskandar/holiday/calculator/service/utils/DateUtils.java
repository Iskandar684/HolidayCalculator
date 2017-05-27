package ru.iskandar.holiday.calculator.service.utils;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
public class DateUtils {

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

}
