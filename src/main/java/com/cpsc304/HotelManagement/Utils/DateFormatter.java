package com.cpsc304.HotelManagement.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatter {
    public static Date addOneDay(Date origin) {
        Date ret = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(origin);
        calendar.add(calendar.DATE,1);
        ret = calendar.getTime();
        System.out.println(ret);
        return ret;
    }

    public static String dateToString(Date origin) {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        String ret = sdf.format(origin);
        System.out.println(ret);
        return ret;
    }

    public static Date stringToDate(String str) {
        Date date = new Date();
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        try {
            date = sdf.parse(str);
        } catch (Exception e) {

        }
        return date;
    }

    public static Integer getLastDayOfMonth(Double dYear, Double dMonth) {
        Integer year = dYear.intValue();
        Integer month  = dMonth.intValue();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.MONTH, month);
        //int lastDay = cal.getActualMaximum(Calendar.DATE);
        int lastDay = cal.getMinimum(Calendar.DATE);
        //cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.DAY_OF_MONTH, lastDay - 1);
        return cal.getTime().getDate();
    }

    public static Date IntegerToDate(Integer year, Integer month, Integer day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static Integer getDateDifference(Date start, Date end) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(start);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        Long lengthOfDays = (cal2.getTime().getTime()-cal1.getTime().getTime())/(24*60*60*1000);
        //System.out.println(lengthOfDays.intValue());
        return lengthOfDays.intValue();
    }
}
