package com.etbike.server.support.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

	public static String getTimeStamp(long targetTimeMillis) {
		String retval = "";
		if (targetTimeMillis <= 0) {
			return retval;
		}

		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTimeInMillis(targetTimeMillis);

		long currentTimeMillis = System.currentTimeMillis();
		Calendar currentCalenda1 = Calendar.getInstance();
		currentCalenda1.setTimeInMillis(currentTimeMillis);
		
		int dayDiff = (int) ((currentTimeMillis - targetTimeMillis) / (1000 * 60 * 60 * 24));

		Calendar onceBeforeCalendar = Calendar.getInstance();
		onceBeforeCalendar.setTimeInMillis(currentTimeMillis);
		onceBeforeCalendar.add(Calendar.DATE, -1);

		Calendar twiceBeforeCalendar = Calendar.getInstance();
		twiceBeforeCalendar.setTimeInMillis(currentTimeMillis);
		twiceBeforeCalendar.add(Calendar.DATE, -2);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (sdf.format(onceBeforeCalendar.getTime()).equals(sdf.format(targetCalendar.getTime()))) {
			retval = "어제";
		} else if (sdf.format(twiceBeforeCalendar.getTime()).equals(
				sdf.format(targetCalendar.getTime()))) {
			retval = "그제";
		} else {
			int minDiff = (int) ((currentTimeMillis - targetTimeMillis) / (1000 * 60));
			if (minDiff < 1) {
				retval = "방금";
			} else if (minDiff < 60) {
				retval = minDiff + "분 전";
			} else if (minDiff < 60 * 24) {
				retval = (int) (minDiff / 60) + "시간 전";
			} else if (dayDiff < 30) {
				retval = dayDiff + "일 전";
			} else if (dayDiff < 364) {
				retval = (int) (dayDiff / (30)) + "달 전";
			} else if (dayDiff < 729) {
				retval = "1년 전";
			} else if (dayDiff > 730) {
				retval = (int) (dayDiff / 365) + "년 전";
			}
		}

		return retval;
	}
}
