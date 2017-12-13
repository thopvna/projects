package com.thopv.projects.ikariam.supports.utils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by thopv on 10/20/2017.
 */

public class DateUtils {
    public final static long SECOND = 1000 ;
    public final static long MINUTE = 1000 * 60;
    public final static long HOUR = MINUTE * 60;
    public final static long DAY = HOUR * 24;
    public final static long WEEK = DAY * 7;
    public final static long MONTH = DAY * 30;
    public final static long YEAR = MONTH * 12;
    public static String getDate(long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.TRADITIONAL_CHINESE).format(date);
    }
    public static boolean isToDay(long timestamp){
        String input = getDate(timestamp);
        String exp = getDate(System.currentTimeMillis());
        return input.equalsIgnoreCase(exp);
    }
    public static long get0hTimeStamp(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static String getDistance(long origin) throws Exception {
        return getAbsDistance(System.currentTimeMillis() - origin);
    }
    public static String getReverseDistance(long origin) throws Exception{
        if(System.currentTimeMillis() - origin > 0){
            throw new Exception(getAbsDistance(System.currentTimeMillis() - origin));
        }
        else{
            return getAbsDistance(origin - System.currentTimeMillis());
        }
    }
    public static String getAbsDistance(long distance) throws Exception {
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
        }
        if(day > 0){
            builder.append(day).append(" ngày ");
        }
        if(hour > 0){
            builder.append(hour).append(" giờ ");
        }
        if(minute > 0){
            builder.append(minute).append(" phút ");
        }
        if((time / 1000) > 0){
            builder.append(time / 1000).append(" giây ");
        }
        if(builder.toString().isEmpty())
            throw new Exception();
        return builder.toString();
    }
    public interface ParseEvents{
        void onParseSecond(long second);
        void onParseMinute(long minute);
        void onParseHour(long hour);
    }
    public static void parse(long timeInMillis, ParseEvents parseEvents){
        parseEvents.onParseHour(timeInMillis / HOUR);
        timeInMillis = timeInMillis % HOUR;
        parseEvents.onParseMinute(timeInMillis / MINUTE);
        timeInMillis = timeInMillis % MINUTE;
        parseEvents.onParseSecond(timeInMillis / SECOND);
    }
}
