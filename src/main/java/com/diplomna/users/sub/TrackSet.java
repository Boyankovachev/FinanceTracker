package com.diplomna.users.sub;

import java.util.List;

public class TrackSet {
    private List<String> names;
    private double notificationPrice;
    public TrackSet(List<String> names, double notificationPrice){
        this.names = names;
        this.notificationPrice = notificationPrice;
    }

    public double getNotificationPrice() {
        return notificationPrice;
    }
    public List<String> getNames(){
        return names;
    }
    public void setNotificationPrice(double notificationPrice){
        this.notificationPrice = notificationPrice;
    }
    public void addName(String name){
        names.add(name);
    }
    public void removeName(String nameToRemove){
        names.remove(nameToRemove);
    }
    public void removeNames(List<String> namesToRemove){
        names.removeAll(namesToRemove);
    }
}
