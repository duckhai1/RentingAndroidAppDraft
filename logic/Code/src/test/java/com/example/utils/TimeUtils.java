package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

public class TimeUtils {

    public static java.sql.Timestamp getTimestamp() {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());

        return new java.sql.Timestamp(cal.getTimeInMillis());
    }

    public static java.sql.Date getDate(int delta) {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, delta);

        return new java.sql.Date(cal.getTimeInMillis());
    }

    public static java.sql.Time getTime(int hours, int minutes, int seconds) {
        var cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        var fmt = new SimpleDateFormat();
        fmt.applyPattern("HH:mm:ss");
        return java.sql.Time.valueOf(fmt.format(cal.getTime()));
    }
}
