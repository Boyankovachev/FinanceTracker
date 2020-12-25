package com.diplomna.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatеManager {
    public SimpleDateFormat myDateFormat;
    public SimpleDateFormat myDateFormatWithTime;
    public SimpleDateFormat sqlDateFormat;
    public Calendar calendar;

    public DatеManager(){
        myDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        myDateFormatWithTime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = new GregorianCalendar();
    }
    public DatеManager(String time){
        myDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        myDateFormatWithTime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = new GregorianCalendar();
        try {
            if(time != null){
                calendar.setTime(sqlDateFormat.parse(time));
            }
            else {
                calendar = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateAsString(){
        if(calendar == null){
            return null;
        }
        return myDateFormat.format(calendar.getTime());
    }
    public String getDateWithTimeAsString(){
        if(calendar == null){
            return null;
        }
        return myDateFormatWithTime.format(calendar.getTime());
    }
    public String getDateSql(){
        if(calendar == null){
            return null;
        }
        return sqlDateFormat.format(calendar.getTime());
    }
}
/*
 Calendar cal = Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
cal.setTime(sdf.parse("Mon Mar 14 16:02:37 GMT 2011"));// all done
 */