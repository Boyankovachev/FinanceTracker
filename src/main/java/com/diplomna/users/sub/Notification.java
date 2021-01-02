package com.diplomna.users.sub;

import com.diplomna.assets.sub.Asset;

import java.util.List;

public class Notification {
    private AssetType assetType;
    private boolean assetTypeSettings; // true - all  false - only 1
    private String notificationTarget;
    private double notificationPrice;
    private String notificationName;
    public Notification(){}
    public Notification(AssetType assetType, double notificationPrice, String notificationName){
        this.assetType = assetType;
        this.notificationPrice = notificationPrice;
        this.notificationName = notificationName;
    }

    public double getNotificationPrice(){
        return notificationPrice;
    }

    public void setNotificationPrice(Double price){
        this.notificationPrice = price;
    }

    public String getNotificationName(){
        return notificationName;
    }

    public void setNotificationName(String name){
        this.notificationName = name;
    }

    public AssetType getAssetType(){
        return assetType;
    }

    public void setAssetType(AssetType assetType){
        this.assetType = assetType;
    }

    public void setAssetTypeSettings(boolean assetTypeSettings) {
        this.assetTypeSettings = assetTypeSettings;
    }
    public boolean getAssetTypeSettings() {
        return assetTypeSettings;
    }
    public void setNotificationTarget(String notificationTarget){
        this.notificationTarget = notificationTarget;
    }
    public String getNotificationTarget(){
        return notificationTarget;
    }

}

/*
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`notification`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `asset_type` ENUM(\"stock\", \"passive_resource\", \"index\", \"crypto\", \"commodity\", \"global\") NOT NULL,\n" +
                    "    `asset_type_settings` BOOL,\n" +
                    "    `notification_target` VARCHAR(12),\n" +
                    "    `notification_price` DOUBLE NOT NULL,\n" +
                    "    `notification_name` VARCHAR(32) NOT NULL,\n" +
                    "    FOREIGN KEY (`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`));\n";
 */
