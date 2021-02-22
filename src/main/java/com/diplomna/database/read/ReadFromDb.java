package com.diplomna.database.read;

import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.ActiveAsset;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.date.DatеManager;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.AssetType;
import com.diplomna.users.sub.Notification;
import com.diplomna.users.sub.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromDb {

    private Connection con;
    private final Logger logger;
    private final String databaseName;

    public ReadFromDb(Connection con, String databaseName){
        this.con = con;
        this.databaseName = databaseName;
        this.logger = LoggerFactory.getLogger(ReadFromDb.class);
    }

    public UserManager readUsers(){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.user;";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getBoolean(6)));
            }
            return new UserManager(users);
        } catch (SQLException throwables) {
            logger.error("readUsers failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Stock> readStockPurchasesByUserId(int userId){
        try{
            String sql = "SELECT * FROM `" + databaseName +"`.stock_purchase_info\n" +
                    "WHERE user_id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<Stock> stocks = new ArrayList<>();
            while (resultSet.next()){
                Stock stock = new Stock();
                stock.setSymbol(resultSet.getString(2));
                PurchaseInfo purchaseInfo = new PurchaseInfo();
                purchaseInfo.setPrice(resultSet.getDouble(3));
                purchaseInfo.setQuantity(resultSet.getDouble(4));
                purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
                stock.addPurchase(purchaseInfo);

                stocks.add(stock);
            }
            return stocks;
        } catch (SQLException throwables) {
            logger.error("readStockPurchaseByUserId failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public Stock readStockBySymbol(String symbol){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.stock\n" +
                    "WHERE symbol = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,symbol);
            ResultSet resultSet = statement.executeQuery();

            Stock stock = new Stock();

            if(!resultSet.next()){
                return null;
            }
            resultSet.beforeFirst();

            while (resultSet.next()) {
                stock.setSymbol(symbol); //  ==  stock.setSymbol(resultSet.getString(1));
                stock.setName(resultSet.getString(2));
                stock.setCurrency(resultSet.getString(3));
                stock.setCurrencySymbol(resultSet.getString(4));
                stock.setExchangeName(resultSet.getString(5));
                stock.setDescription(resultSet.getString(6));
                stock.setCurrentMarketPrice(resultSet.getDouble(7));
                stock.setMarketOpen(resultSet.getBoolean(8));
                stock.setRecommendationKey(resultSet.getString(9));
            }
            return stock;
        } catch (SQLException throwables) {
            logger.error("readStockBySymbol failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Stock> readAllStocks() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.stock;";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Stock> stocks = new ArrayList<>();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            Stock stock = new Stock();
            stock.setSymbol(resultSet.getString(1));
            stock.setName(resultSet.getString(2));
            stock.setCurrency(resultSet.getString(3));
            stock.setCurrencySymbol(resultSet.getString(4));
            stock.setExchangeName(resultSet.getString(5));
            stock.setDescription(resultSet.getString(6));
            stock.setCurrentMarketPrice(resultSet.getDouble(7));
            stock.setMarketOpen(resultSet.getBoolean(8));
            stock.setRecommendationKey(resultSet.getString(9));

            stocks.add(stock);
        }
        return stocks;
    }

    public List<PassiveResource> readPassiveResourcesByUserId(int userId){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.passive_resource\n" +
                    "WHERE user_id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<PassiveResource> passiveResources = new ArrayList<>();
            while (resultSet.next()){
                PassiveResource passiveResource = new PassiveResource();
                passiveResource.setName(resultSet.getString(2));
                PurchaseInfo purchaseInfo = new PurchaseInfo();
                purchaseInfo.setPrice(resultSet.getDouble(3));
                purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(4)));
                passiveResource.setPurchaseInfo(purchaseInfo);
                passiveResource.setCurrentMarketPrice(resultSet.getDouble(5));
                passiveResource.setDescription(resultSet.getString(6));
                passiveResource.setCurrency(resultSet.getString(7));
                passiveResource.setCurrencySymbol(resultSet.getString(8));

                passiveResources.add(passiveResource);
            }
            return passiveResources;
        } catch (SQLException throwables) {
            logger.error("readPassiveResourceByUserId failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public Index readIndexBySymbol(String symbol){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.index\n" +
                    "WHERE symbol = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,symbol);
            ResultSet resultSet = statement.executeQuery();

            Index index = new Index();

            if(!resultSet.next()){
                return null;
            }
            resultSet.beforeFirst();

            while (resultSet.next()) {
                index.setSymbol(symbol); //  ==  index.setSymbol(resultSet.getString(1));
                index.setName(resultSet.getString(2));
                index.setCurrency(resultSet.getString(3));
                index.setCurrencySymbol(resultSet.getString(4));
                index.setExchangeName(resultSet.getString(5));
                index.setDescription(resultSet.getString(6));
                index.setCurrentMarketPrice(resultSet.getDouble(7));
                index.setMarketOpen(resultSet.getBoolean(8));
            }
            return index;
        } catch (SQLException throwables) {
            logger.error("readIndexBySymbol failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Index> readAllIndex() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.index;";

        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Index> indexList = new ArrayList<>();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        while (resultSet.next()) {
            Index index = new Index();

            index.setSymbol(resultSet.getString(1));
            index.setName(resultSet.getString(2));
            index.setCurrency(resultSet.getString(3));
            index.setCurrencySymbol(resultSet.getString(4));
            index.setExchangeName(resultSet.getString(5));
            index.setDescription(resultSet.getString(6));
            index.setCurrentMarketPrice(resultSet.getDouble(7));
            index.setMarketOpen(resultSet.getBoolean(8));

            indexList.add(index);
        }
        return indexList;
    }

    public List<Index> readIndexPurchasesByUserId(int userId){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.index_purchase_info\n" +
                    "WHERE user_id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<Index> indexList = new ArrayList<>();
            while (resultSet.next()){
                Index index = new Index();
                index.setSymbol(resultSet.getString(2));
                PurchaseInfo purchaseInfo = new PurchaseInfo();
                purchaseInfo.setPrice(resultSet.getDouble(3));
                purchaseInfo.setQuantity(resultSet.getDouble(4));
                purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
                index.addPurchase(purchaseInfo);

                indexList.add(index);
            }
            return indexList;
        } catch (SQLException throwables) {
            logger.error("readIndexPurchasesByUserId failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public Crypto readCryptoBySymbol(String symbol){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.crypto\n" +
                    "WHERE symbol = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,symbol);
            ResultSet resultSet = statement.executeQuery();
            Crypto crypto = new Crypto();

            if(!resultSet.next()){
                return null;
            }
            resultSet.beforeFirst();

            while (resultSet.next()) {
                crypto.setSymbol(symbol); //  ==  crypto.setSymbol(resultSet.getString(1));
                crypto.setName(resultSet.getString(2));
                crypto.setCurrency(resultSet.getString(3));
                crypto.setCurrencySymbol(resultSet.getString(4));
                crypto.setDescription(resultSet.getString(5));
                crypto.setCurrentMarketPrice(resultSet.getDouble(6));
            }
            return crypto;
        } catch (SQLException throwables) {
            logger.error("readCryptoBySymbol failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Crypto> readAllCrypto() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.crypto;";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        List<Crypto> cryptos = new ArrayList<>();

        while (resultSet.next()) {
            Crypto crypto = new Crypto();
            crypto.setSymbol(resultSet.getString(1));
            crypto.setName(resultSet.getString(2));
            crypto.setCurrency(resultSet.getString(3));
            crypto.setCurrencySymbol(resultSet.getString(4));
            crypto.setDescription(resultSet.getString(5));
            crypto.setCurrentMarketPrice(resultSet.getDouble(6));
            cryptos.add(crypto);
        }
        return cryptos;
    }

    public List<Crypto> readCryptoPurchaseByUserId(int userId){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.crypto_purchase_info\n" +
                    "WHERE user_id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<Crypto> cryptoList = new ArrayList<>();
            while (resultSet.next()){
                Crypto crypto = new Crypto();
                crypto.setSymbol(resultSet.getString(2));
                PurchaseInfo purchaseInfo = new PurchaseInfo();
                purchaseInfo.setPrice(resultSet.getDouble(3));
                purchaseInfo.setQuantity(resultSet.getDouble(4));
                purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
                crypto.addPurchase(purchaseInfo);

                cryptoList.add(crypto);
            }
            return cryptoList;
        } catch (SQLException throwables) {
            logger.error("readCryptoPurchaseByUserId failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public Commodities readCommodityByCommodityName(String commodityName){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.commodity\n" +
                    "WHERE commodity_name = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,commodityName);
            ResultSet resultSet = statement.executeQuery();
            Commodities commodity = new Commodities();

            if(!resultSet.next()){
                return null;
            }
            resultSet.beforeFirst();

            while (resultSet.next()) {
                commodity.setName(commodityName); //  ==  commodity.setName(resultSet.getString(1));
                commodity.setCurrency(resultSet.getString(2));
                commodity.setCurrencySymbol(resultSet.getString(3));
                commodity.setExchangeName(resultSet.getString(4));
                commodity.setDescription(resultSet.getString(5));
                commodity.setCurrentMarketPrice(resultSet.getDouble(6));
            }
            return commodity;
        } catch (SQLException throwables) {
            logger.error("readCommodityByCommodityName failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }
    public List<Commodities> readAllCommodities() throws SQLException {
        String sql = "SELECT * FROM `" + databaseName +"`.commodity;";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.next()){
            return null;
        }
        resultSet.beforeFirst();

        List<Commodities> commodities = new ArrayList<>();

        while (resultSet.next()) {
            Commodities commodity = new Commodities();
            commodity.setName(resultSet.getString(1));
            commodity.setCurrency(resultSet.getString(2));
            commodity.setCurrencySymbol(resultSet.getString(3));
            commodity.setExchangeName(resultSet.getString(4));
            commodity.setDescription(resultSet.getString(5));
            commodity.setCurrentMarketPrice(resultSet.getDouble(6));
            commodities.add(commodity);
        }
        return commodities;
    }

    public List<Commodities> readCommodityPurchaseInfoByUserId(int userId){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.commodity_purchase_info\n" +
                    "WHERE user_id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<Commodities> commoditiesList = new ArrayList<>();
            while (resultSet.next()){
                Commodities commodity = new Commodities();
                commodity.setName(resultSet.getString(2));
                PurchaseInfo purchaseInfo = new PurchaseInfo();
                purchaseInfo.setPrice(resultSet.getDouble(3));
                purchaseInfo.setQuantity(resultSet.getDouble(4));
                purchaseInfo.setPurchaseDate(new DatеManager(resultSet.getString(5)));
                commodity.addPurchase(purchaseInfo);

                commoditiesList.add(commodity);
            }
            return commoditiesList;
        } catch (SQLException throwables) {
            logger.error("readCommodityPurchaseInfoByUserId failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Notification> readNotificationsByUserId(int userId){
        try {
            String sql = "SELECT * FROM `" + databaseName +"`.notification\n" +
                    "WHERE user_id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()){
                return null;
            }
            resultSet.beforeFirst();

            List<Notification> notifications = new ArrayList<>();
            while (resultSet.next()){
                Notification notification = new Notification();
                notification.setAssetType(AssetType.valueOf(resultSet.getString(2)));
                notification.setAssetTypeSettings(resultSet.getBoolean(3));
                notification.setNotificationTarget(resultSet.getString(4));
                notification.setNotificationPrice(resultSet.getDouble(5));
                notification.setNotificationName(resultSet.getString(6));
                notifications.add(notification);
            }
            return notifications;
        } catch (SQLException throwables) {
            logger.error("readNotificationsByUserId failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    public List<String> readPresentAsset(String assetType){
        try {
            switch (assetType) {
                case "stock":
                    return readAllStocksSymbols();
                case "index":
                    return readAllIndexSymbols();
                case "crypto":
                    return readAllCryptoSymbols();
                case "commodity":
                    return readAllCommodityNames();
            }
        }
        catch (SQLException throwables){
            logger.error("readPresentAsset failed\n" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    private List<String> readAllStocksSymbols() throws SQLException {
        String sql = "SELECT `symbol` FROM `" + databaseName +"`.stock";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> stocks = new ArrayList<>();
        while (resultSet.next()) {
            stocks.add(resultSet.getString(1));
        }
        return stocks;
    }

    private List<String> readAllIndexSymbols() throws SQLException {
        String sql = "SELECT `symbol` FROM `" + databaseName +"`.index\n";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> indexes = new ArrayList<>();
        while (resultSet.next()) {
            indexes.add(resultSet.getString(1));
        }
        return indexes;
    }

    private List<String> readAllCryptoSymbols() throws SQLException {
        String sql = "SELECT `symbol` FROM `" + databaseName +"`.crypto";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> cryptos = new ArrayList<>();
        while (resultSet.next()) {
            cryptos.add(resultSet.getString(1));
        }
        return cryptos;
    }

    private List<String> readAllCommodityNames() throws SQLException {
        String sql = "SELECT `commodity_name` FROM `" + databaseName +"`.commodity";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> commodity = new ArrayList<>();
        while (resultSet.next()) {
            commodity.add(resultSet.getString(1));
        }
        return commodity;
    }

}
