package com.diplomna.users.sub;

import com.diplomna.assets.sub.Asset;

import java.util.List;

public class Notification {
    AssetType assetType;
    private double notificationPrice;
    private String notificationName;
    private String stockSymbol;
    private String passiveAssetName;
    public Notification(){}
    public Notification(AssetType assetType, double notificationPrice, String notificationName){
        this.assetType = assetType;
        this.notificationPrice = notificationPrice;
        this.notificationName = notificationName;
    }

    public double getNotificationPrice(){
        return notificationPrice;
    }

    public String getNotificationName(){
        return notificationName;
    }

    public AssetType getAssetType(){
        return assetType;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getPassiveAssetName() {
        return passiveAssetName;
    }

    public void setPassiveAssetName(String passiveAssetName) {
        this.passiveAssetName = passiveAssetName;
    }

}

/*
            String sql = "CREATE TABLE IF NOT EXISTS `"+databaseName+"`.`notification`(\n" +
                    "    `user_id` INT NOT NULL,\n" +
                    "    `asset_type` ENUM(\"stock\", \"passive_resource\", \"global\") NOT NULL,\n" +
                    "    `notification_price` DOUBLE NOT NULL,\n" +
                    "    `notification_name` VARCHAR(32) NOT NULL,\n" +
                    "    `stock_symbol` VARCHAR(12),\n" +
                    "    `passive_asset_name` VARCHAR(12),\n" +
                    "    FOREIGN KEY (`user_id`) REFERENCES `" + databaseName + "`.user(`user_id`));\n";
 */
