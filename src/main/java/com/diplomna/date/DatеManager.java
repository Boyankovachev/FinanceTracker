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
    private String myFormatDateString;

    public DatеManager(){
        myDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        myDateFormatWithTime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = new GregorianCalendar();
        myFormatDateString = myDateFormat.format(calendar.getTime());
    }
    public DatеManager(String time){
        myDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        myDateFormatWithTime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = new GregorianCalendar();
        try {
            if(time != null){
                calendar.setTime(sqlDateFormat.parse(time));
                myFormatDateString = myDateFormat.format(calendar.getTime());
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

    public String getMyFormatDateString() {
        return myFormatDateString;
    }

    public void setDateFromString(String date) throws ParseException {
        calendar.setTime(myDateFormat.parse(date));
    }
}