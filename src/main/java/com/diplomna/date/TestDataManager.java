package com.diplomna.date;

import java.util.Date;

public class TestDataManager {
    static public void main(String []args){
        /*
        DatеManager dataManager = new DatеManager();

        System.out.println(dataManager.getDateAsString() + "\n" + dataManager.getDateWithTimeAsString());

        DatеManager datеManagerSql = new DatеManager("2020-11-14");
        System.out.println(datеManagerSql.getDateSql());

         */
        DatеManager datеManager = new DatеManager("2020-12-25");
        System.out.println(datеManager.getMyFormatDateString());

        DatеManager datеManager2 = new DatеManager();
        System.out.println(datеManager2.getMyFormatDateString());
    }
}
