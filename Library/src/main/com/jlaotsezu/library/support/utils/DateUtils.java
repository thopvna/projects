package main.com.jlaotsezu.library.support.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getBasicDate(long timeInMillis){
        if(timeInMillis <= 0)
            return "--/--/----";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date(timeInMillis));
    }
}
