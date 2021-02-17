package com.diplomna.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;

@JsonSerialize
@JsonComponent // i bez tiq 2te raboti '_'
               // ama pisheshe che trqq da gi ima ~_~
public class GraphInfo{
    /*
        Class used for sending graph info
        data as a POJO class to client side
        javascript handling
     */
    private int day;
    private int month;
    private int year;
    private double price;

    public GraphInfo(){}

    public GraphInfo(int day, int month, int year, double price){
        this.day = day;
        this.month = month;
        this.year = year;
        this.price = price;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
