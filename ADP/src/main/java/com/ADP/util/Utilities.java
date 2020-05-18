package com.ADP.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.ADP.util.spcs.*;
import java.awt.geom.Point2D;

public class Utilities {

    public static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static DateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a");
    public static DateFormat DEFAULT_SHORT_TIME_FORMAT = new SimpleDateFormat("hh:mm a");
    public static int zone = -1;
    public static String MMAPI_Url = "";
//    public int getzone(){    	
//    	return this.zone;
//    }
//    public void setzone(int value){    	
//    	this.zone = value;
//    }
    public static int Compare2Date(String s1, String s2) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        int i = 0;
        try {
            Date d1 = sdf.parse(s1);
            Date d2 = sdf.parse(s2);
            i = d1.compareTo(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String isnull(String tmp) {
        if (tmp == null) {
            tmp = "";
        }
        return tmp.trim();
    }

    public static String paddingString(String s, int n, char c, boolean paddingLeft) {
        StringBuffer str = new StringBuffer(s);
        int strLength = str.length();
        if (n > 0 && n > strLength) {
            for (int i = 0; i <= n; i++) {
                if (paddingLeft) {
                    if (i < n - strLength) {
                        str.insert(0, c);
                    }
                } else {
                    if (i > strLength) {
                        str.append(c);
                    }
                }
            }
        }
        return str.toString();
    }

    public static String AMPM2HHSS(String sValue) {
        String tmp = sValue;
        String hour, minus, second, amp;
        int mhour;
        if (sValue.trim().equals("")) {
            return "";
        }
        if (sValue == null) {
            return "";
        }
        if (tmp.trim().equals("00:00:00")) {
            return "00:00:00";
        }
        if (tmp.trim().equals("12:00:00 AM")) {
            return "00:00:00";
        }
        if (tmp.trim().equals("12:00:00 PM")) {
            return "12:00:00";
        }
        tmp = tmp.trim();
        hour = tmp.substring(0, 2);
        minus = tmp.substring(3, 5);
        second = tmp.substring(6, 8);
        amp = tmp.substring(9);

        mhour = Integer.parseInt(hour);
        if (amp.trim().equals("PM")) {
            if (mhour < 12) {
                mhour = Integer.parseInt(hour) + 12;
            }

        }
        hour = Integer.toString(mhour);
        if (hour.equals("12") && !amp.trim().equals("PM")) {
            hour = "0";
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }

        return hour + ":" + minus + ":" + second;

    }

    public static String AMPM2HHSSNotIncludeSecond(String sValue) {

        String tmp = sValue;
        String hour, minus, amp;

        int mhour;

        if (tmp.trim().equals("12:00 AM")) {
            return "00:00:00";
        }
        if (tmp.trim().equals("12:00 PM")) {
            return "12:00:00";
        }
        String[] arrTime = sValue.split(":");
        hour = arrTime[0].trim();
        //minus = arrTime[1].trim();
        String[] Minus = arrTime[1].trim().split(" ");
        minus = Minus[0].trim();
        amp = Minus[1].trim();
        if (minus.length() == 1) {
            minus = "0" + minus;
        }

        mhour = Integer.parseInt(hour);
        if (mhour == 12) {
            mhour = 0;
        }
        if (amp.equals("PM")) {
            mhour = mhour + 12;
        }
        hour = Integer.toString(mhour);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return hour + ":" + minus + ":00";
    }

    public static int Compare2DateTime(String s1, String s2) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        int i = 0;
        try {
            Date d1 = sdf.parse(s1);
            Date d2 = sdf.parse(s2);
            i = d1.compareTo(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return i;
    }

    public static String ConvertPasswordAscii(String strBuffer) {
        String strNtPass = "";
        strNtPass = strNtPass + Encode_Pass(Double.parseDouble(getSubstringEx(strBuffer, 0, 6)));
        strNtPass = strNtPass + Encode_Pass(Double.parseDouble(getSubstringEx(strBuffer, 6, 6)));
        strNtPass = strNtPass + Encode_Pass(Double.parseDouble(getSubstringEx(strBuffer, 12, 6)));
        strNtPass = strNtPass + Encode_Pass(Double.parseDouble(getSubstringEx(strBuffer, 18, 6)));
        strNtPass = strNtPass + Encode_Pass(Double.parseDouble(getSubstringEx(strBuffer, 24, 6)));

        return strNtPass.trim();
    }

    private static String getSubstringEx(String strTemp, int startIdx, int len) {
        String retStr = getSubstring(strTemp, startIdx, len);
        if (retStr.equals("")) {
            retStr = "0";
        }
        return retStr;
    }

    public static String getSubstring(String strTemp, int startIdx, int len) {
        String retStr = "";
        int lenStr = strTemp.length();
        if (startIdx >= lenStr) {
            retStr = "";
        } else if (startIdx + len < lenStr) {
            retStr = strTemp.substring(startIdx, startIdx + len);
        } else {
            retStr = strTemp.substring(startIdx);
        }
        return retStr;
    }

    public static String Encode_Pass(double intId) {
        double a = 0, b = 0, c = 0;
        int intToAscciMode = 2;
        String strAsciiTemp = "", strAscii;
        int intTemp = 0, intTempa = 0, intTempb = 0, i4id = (int) intId;
        intTemp = (int) (i4id / (69 * 69));
        a = intTemp;
        intTempa = intTemp * 69 * 69;
        intTemp = i4id - intTempa;
        intTemp = (int) (intTemp / 69);
        b = intTemp;
        intTempb = intTemp * 69;
        c = i4id - intTempa - intTempb;
        // .. mode 2 = numbers to ascci
        strAscii = Convert_37(strAsciiTemp, a, intToAscciMode);
        strAscii = strAscii + Convert_37(strAsciiTemp, b, intToAscciMode);
        strAscii = strAscii + Convert_37(strAsciiTemp, c, intToAscciMode);

        return strAscii;
    }

    public static String Convert_37(String strAscii, double intIndex, int intMode) {
        double intTemp = 0;
        try {
            if (intMode == 1) {
                //intTemp = Asc(strAscii);
                char chrAscc = strAscii.toCharArray()[0];
                intTemp = (int) chrAscc;
                if (intTemp == 32) {
                    intIndex = intTemp - 32;
                } //strAscii : 0-9
                else if (intTemp <= 57 && intTemp >= 48) {
                    intIndex = intTemp - 47;
                } //strAscii: A-Z
                else if (intTemp <= 90 && intTemp >= 65) {
                    intIndex = intTemp - 54;
                } //strAscii: a-z
                else if (intTemp <= 122 && intTemp >= 97) {
                    intIndex = intTemp - 54;
                }
                return Integer.toString((int) intIndex);
            } else if (intMode == 2) {
                if (intIndex == 0) {
                    intTemp = intIndex + 32;
                } //0-9
                else if (intIndex <= 10 && intIndex >= 1) {
                    intTemp = intIndex + 47;
                } //A-Z
                else if (intIndex <= 36 && intIndex >= 11) {
                    intTemp = intIndex + 54;
                } //a-z
                else if (intIndex <= 68 && intIndex >= 43) {
                    intTemp = intIndex + 54;
                }
                //return Chr(intTemp);
                return Character.toString((char) ((int) intTemp));
            }
        } catch (Exception ee) {
            return "0";
        }
        return "";
    }

    public static boolean IsAlpha(String strCha) {
        char chrAscc = strCha.toCharArray()[0];
        int intAscii = (int) chrAscc;
        if ((intAscii <= 90 && intAscii >= 65) || (intAscii <= 122 && intAscii >= 97)) {
            return true;
        }
        return false;
    }

    public static String ConvertAsciiPassword_69(String strNtPass) {
        String strBuffer = "";
        strBuffer = strBuffer + FormatString_69(Decode_Pass_69(getSubstring(strNtPass, 0, 3)));
        strBuffer = strBuffer + FormatString_69(Decode_Pass_69(getSubstring(strNtPass, 3, 3)));
        strBuffer = strBuffer + FormatString_69(Decode_Pass_69(getSubstring(strNtPass, 6, 3)));
        strBuffer = strBuffer + FormatString_69(Decode_Pass_69(getSubstring(strNtPass, 9, 3)));
        strBuffer = strBuffer + FormatString_69(Decode_Pass_69(getSubstring(strNtPass, 12, 3)));

        return strBuffer;
    }

    private static String FormatString_69(double dblDecode) {
        //String strFormat = Double.toString(dblDecode);
        String strFormat = Integer.toString((int) dblDecode);
        int intI;
        String strSpace = "";

        if (strFormat.length() < 6) {
            for (intI = strFormat.length() + 1; intI <= 6; intI++) {
                strSpace = " " + strSpace;
            }
        }
        return strSpace + strFormat;
    }

    public static double Decode_Pass_69(String strAscii) {
        int a = 0, b = 0, c = 0, intToNumberMode = 1;
        double intTemp = 0;  
        a = Integer.parseInt(Convert_37(getSubstring(strAscii, 0, 1), intTemp, intToNumberMode));
        b = Integer.parseInt(Convert_37(getSubstring(strAscii, 1, 1), intTemp, intToNumberMode));
        c = Integer.parseInt(Convert_37(getSubstring(strAscii, 2, 1), intTemp, intToNumberMode));

        intTemp = c;
        intTemp = intTemp + (69 * b);
        intTemp = intTemp + (69 * 69 * a);
        return intTemp;
    }
    
    public static Point2D.Double computeLongLatForStatePlane(double x, double y, int zone)
    {   
      
      GEODETIC geo = new GEODETIC();
      GEO_XY xy = new GEO_XY();
      LatLonSPCSCon con = new LatLonSPCSCon();
      geo.coordinates = 0;
      geo.conversion = 1;
      geo.cons = new constants();     
      xy.x = x;
      xy.y = y;          
      geo.data = xy;
      con.build_geodetic(zone,0.0f,0.0f,geo);
      con.convert_to_geodetic(geo);
      double longitude = geo.data.lon;
      double latitude = geo.data.lat;  
      // longitude needs to be negative for western hemisphere  
      Point2D.Double longLatPoint = new Point2D.Double(-longitude, latitude);     
      
      return longLatPoint;
    }
    
    public static String LogReceive (long timekey, String type, String byType, String vari)
    {    	
    	return "[" + timekey + "] Get " + type + " by " + byType + " = [" + vari + "].";
    }
    
    public static String LogReturn (long timekey, String type, String byType, String vari)
    {
    	long curtime = DateFunction.TimeKey();
    	long duration = curtime-timekey;
    	return "[" + duration + " ms] Get " + type + " by " + byType + " = [" + vari + "] is completed.";
    }
    
    public static String LogSave (long timekey, String type, String byType, String vari)
    {
    	long curtime = DateFunction.TimeKey();
    	long duration = curtime-timekey;
    	return "[" + duration + " ms] Add/Update " + type + " by " + byType + " = [" + vari + "] is completed.";
    }
    
    public static String LogError (long timekey, String type, String byType, String vari, String error)
    {
    	long curtime = DateFunction.TimeKey();
    	long duration = curtime-timekey;
    	return "[" + duration + " ms] Get " + type + " by " + byType + " = [" + vari + "] that were returned error: " + error;
    }

}
