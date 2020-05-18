package com.APIMM.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateFunction {

	public static String MMddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
	public static String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";
	public static String MMddyyyy = "MM/dd/yyyy";
	public static String yyyyMMdd = "yyyy/MM/dd";
	public static String yyyyMMddHHmmss = "yyyy/MM/dd";
	public static long TimeKey(){
		Date dKey = new Date();
		long timekey = dKey.getTime();
		return timekey;
	}
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
	
	public static Timestamp ChangeTimewithZone(Timestamp ts) {
		if(ts == null) return null;
		DateTimeZone tz = DateTimeZone.getDefault();
	    Long instant = DateTime.now().getMillis();	    
	    long offsetInMilliseconds = tz.getOffset(instant);	   
		return new Timestamp(ts.getTime() + offsetInMilliseconds);
	}
	
	public static boolean CheckValidTime(String time) 
	{	
		try
		{	
			if (time.length()<6) time = time+":00";
			DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			Date date = sdf.parse(time);
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
		
	}
	public static Date convertToUTC(Date date, String timeZone) {
		try {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    TimeZone timeZoneMM = TimeZone.getTimeZone(timeZone.trim());
	    sdf.setTimeZone(timeZoneMM);
	    
	    String dateN = sdf.format(date);
	    SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
	    Date gmt = sdfUTC.parse(dateN);
	    
	    long secs = (date.getTime() - gmt.getTime()) / 1000;
	    long hours = secs / 3600;
	    
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR, (int)hours);
	    //cal.set(Calendar.MILLISECOND, 0);
	    gmt = sdfUTC.parse(sdfUTC.format(cal.getTime()));
	    return gmt;
		}catch( Exception e) {
	    	e.printStackTrace();
	    }
		return date;
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
   public static Date convertUTCToZone(Date date, String timeZone) {
	      try {
	         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	         TimeZone timeZoneMM = TimeZone.getTimeZone(timeZone.trim());
	         sdf.setTimeZone(timeZoneMM);
	         String dateN = sdf.format(date);
	         SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	         sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
	         Date gmt = sdfUTC.parse(dateN);
	         long secs = (gmt.getTime() - date.getTime()) / 1000L;
	         long hours = secs / 3600L;
	         Calendar cal = Calendar.getInstance();
	         cal.setTime(date);
	         cal.add(10, (int)hours);
	         gmt = sdfUTC.parse(sdfUTC.format(cal.getTime()));
	         return gmt;
	      } catch (Exception var12) {
	         var12.printStackTrace();
	         return date;
	      }
	   }

}
