package com.diplomna.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatеManager {
    public SimpleDateFormat myDateFormat;
    public SimpleDateFormat myDateFormatWithTime;
    public Calendar calendar;

    public DatеManager(){
        myDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        myDateFormatWithTime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        calendar = new GregorianCalendar();
    }

    public String getDateAsString(){
        return myDateFormat.format(calendar.getTime());
    }
    public String getDateWithTimeAsString(){
        return myDateFormatWithTime.format(calendar.getTime());
    }
}
