package com.ADP.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateFunction {

	public static String MMddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
	public static String MMddyyyy = "MM/dd/yyyy";
	public static String yyyyMMdd = "yyyy/MM/dd";
	public static String yyyyMMddHHmmss = "yyyy/MM/dd";
	public static String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";

	public static String DateTimeToString(Timestamp ts, String format) {
		if (ts == null)
			return "";
		return new SimpleDateFormat(format).format(ts);
	}
	
	public static String DateTimeToString(Date ts, String format) {
		if (ts == null)
			return "";
		return new SimpleDateFormat(format).format(ts);
	}
	public static int CompareDate(Timestamp ts1, Timestamp ts2) {
		SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMdd);
		try {

			Date d1 = formatter.parse(DateTimeToString(ts1, yyyyMMdd));
			Date d2 = formatter.parse(DateTimeToString(ts2, yyyyMMdd));
			return d1.compareTo(d2);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static Timestamp CurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String CurrentTimeStamp(String format) {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(d);
		return dateString;
	}

	public static Timestamp NextTimeStamp() {
		DateTime currentdate = new DateTime(System.currentTimeMillis());
		return new Timestamp(currentdate.plusDays(1).getMillis());
	}

	public static String NextTimeStamp(String format) {
		DateTime currentdate = new DateTime(System.currentTimeMillis());
		return currentdate.plusDays(1).toString(format);
	}

	public static Timestamp AddTimeZone(Timestamp ts) {
		if(ts == null) return null;
		DateTimeZone tz = DateTimeZone.getDefault();
	    Long instant = DateTime.now().getMillis();	    
	    long offsetInMilliseconds = tz.getOffset(instant);	   
		return new Timestamp(ts.getTime() - offsetInMilliseconds);
	}
	public static Timestamp MoveTimeZone(Timestamp ts) {
		if(ts == null) return null;
		DateTimeZone tz = DateTimeZone.getDefault();
	    Long instant = DateTime.now().getMillis();	    
	    long offsetInMilliseconds = tz.getOffset(instant);	   
		return new Timestamp(ts.getTime() + offsetInMilliseconds);
	}
	
	public static long TimeKey()
	{
		Date dKey = new Date();
		return dKey.getTime();
	}
	
	public static Date StringToDate(String strdate, String strformat) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(strformat);
		try {
			if(strdate == null) return null;
			return formatter.parse(strdate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static Date ConvertDateClient(Date dt)
//	{
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(dt);
//		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-(calendar.get(Calendar.ZONE_OFFSET)/(1000*60*60)));
//		return calendar.getTime();
//	
//	}
}
