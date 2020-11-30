package com.diplomna.date;

public class TestDataManager {
    static public void main(String []args){
        DatеManager dataManager = new DatеManager();

        System.out.println(dataManager.getDateAsString() + "\n" + dataManager.getDateWithTimeAsString());
    }
}
