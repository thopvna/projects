package com.thopv.projects.libraryforreader.support.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by thopv on 11/11/2017.
 */

public class DateUtils {
    public final static long MINUTE = 1000 * 60;
    public  final static long HOUR = MINUTE * 60;
    public  final static long DAY = HOUR * 24;
    public final  static long WEEK = DAY * 7;
    public final  static long MONTH = DAY * 30;
    public final  static long YEAR = MONTH * 12;
    public static String getDate(long timeInMillis){
        Date date = new Date(timeInMillis);
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(date);
    }
    public static String getDistanceFromNow(long origin) throws Exception{
        return getDistance(System.currentTimeMillis() - origin);
    }
    public static String getDistance(long distance) throws Exception {
        long time = Math.abs(distance);
        int year  = (int) (time / YEAR);
        time -= year * YEAR;
        int month = (int) (time / MONTH);
        time -= month * MONTH;
        int day = (int) (time / DAY);
        time -= day * DAY;
        int hour = (int) (time / HOUR);
        time -= hour * HOUR;
        int minute = (int) (time / MINUTE);
        time -= minute * MINUTE;
        StringBuilder builder = new StringBuilder();
        if(year > 0){
            builder.append(year).append(" năm ");
        }
        if(month > 0){
            builder.append(month).append(" tháng ");
            if(year > 0)
                return builder.toString();
        }
        if(day > 0){
            builder.append(day).append(" ngày ");
            if(month > 0)
                return builder.toString();
        }
        if(hour > 0){
            builder.append(hour).append(" giờ ");
            if(day > 0)
                return builder.toString();
        }
        if(minute > 0){
            builder.append(minute).append(" phút ");
            if(hour > 0)
                return builder.toString();
        }
        if((time / 1000) > 0){
            builder.append(time / 1000).append(" giây ");
        }
        if(builder.toString().isEmpty())
            throw new Exception();
        return builder.toString();
    }

}
