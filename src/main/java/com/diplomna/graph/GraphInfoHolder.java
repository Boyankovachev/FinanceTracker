package com.diplomna.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphInfoHolder {
    private List<GraphInfo> daily = new ArrayList<>();
    private List<GraphInfo> weekly = new ArrayList<>();
    private List<GraphInfo> monthly = new ArrayList<>();

    private String assetSymbol;

    public GraphInfoHolder(){}


    public boolean isDailyEmpty(){
        //check if daily list is empty
        //return true if empty
        return (daily == null) || daily.isEmpty();
    }
    public boolean isWeeklyEmpty(){
        //check if weekly list is empty
        //return true if empty
        return (weekly == null) || weekly.isEmpty();
    }
    public boolean isMonthlyEmpty(){
        //check if monthly list is empty
        //return true if empty
        return (monthly == null) || monthly.isEmpty();
    }

    public void updateDaily(List<GraphInfo> updatedDaily){
        daily.clear();
        daily.addAll(updatedDaily);
    }
    public void updateWeekly(List<GraphInfo> updatedWeekly){
        weekly.clear();
        weekly.addAll(updatedWeekly);
    }
    public void updateMonthly(List<GraphInfo> updatedMonthly){
        monthly.clear();
        monthly.addAll(updatedMonthly);
    }

    //Getters and Setters below
    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public List<GraphInfo> getDaily() {
        if(daily == null){
            return null;
        }
        return daily;
    }

    public List<GraphInfo> getMonthly() {
        if(monthly == null){
            return null;
        }
        return monthly;
    }

    public List<GraphInfo> getWeekly() {
        if(weekly == null){
            return null;
        }
        return weekly;
    }

    public void setDaily(List<GraphInfo> daily) {
        this.daily = daily;
    }

    public void setMonthly(List<GraphInfo> monthly) {
        this.monthly = monthly;
    }

    public void setWeekly(List<GraphInfo> weekly) {
        this.weekly = weekly;
    }
}
