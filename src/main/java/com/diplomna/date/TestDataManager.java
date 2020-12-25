package com.diplomna.date;

import java.util.Date;

public class TestDataManager {
    static public void main(String []args){
        DatеManager dataManager = new DatеManager();

        System.out.println(dataManager.getDateAsString() + "\n" + dataManager.getDateWithTimeAsString());

        DatеManager datеManagerSql = new DatеManager("2020-11-14");
        System.out.println(datеManagerSql.getDateSql());
    }
}
