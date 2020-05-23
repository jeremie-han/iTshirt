package com.escape.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public static Date stringToDate(String DateStr, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(DateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String date2str(Date dt) {
		return date2str(dt, "yyyyMMddHHmmss");
	}

	public static String date2str(Date dt, String pattern) {
		Calendar cal = Calendar.getInstance();
		String ptn = null;
		if (dt == null)
			return null;
		if (pattern == null)
			return null;
		ptn = pattern.trim();
		if (ptn.length() < 1)
			return null;
		SimpleDateFormat dateHeader = new SimpleDateFormat(ptn);
		return dateHeader.format(cal.getTime());
	}
	
	

}
