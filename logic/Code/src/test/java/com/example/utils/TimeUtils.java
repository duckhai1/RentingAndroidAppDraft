package com.example.utils;

import java.util.Calendar;
import java.util.logging.Logger;

public class TimeUtils {

    private final static Logger LOG = Logger.getLogger("UTILS");

    public static java.sql.Timestamp getTimestamp() {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());

        var date = new java.sql.Timestamp(cal.getTimeInMillis());
        LOG.info("getDate() " + date);
        return date;
    }

    public static java.sql.Date getDate(int delta) {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, delta);

        var date = new java.sql.Date(cal.getTimeInMillis());
        LOG.info("getDate() " + date);
        return date;
    }

    public static java.sql.Time getTime(int hours, int minutes, int seconds) {
        var cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        var time = new java.sql.Time(cal.getTimeInMillis());
        LOG.info("getTime() " + time);
        return time;
    }
}
