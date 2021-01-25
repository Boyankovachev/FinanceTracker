package com.diplomna.api.stock;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseStock {
    private HttpResponse<JsonNode> stock;
    private final Logger logger;
    public ParseStock() {
        logger= LoggerFactory.getLogger(ParseStock.class);
    }
    public ParseStock(String symbol) throws UnirestException {
        GetStockFromAPI stockGetter = new GetStockFromAPI();
        stock = stockGetter.getStockBySymbolAsJSON(symbol);
        logger= LoggerFactory.getLogger(ParseStock.class);
    }

    public void setStockBySymbol(String symbol) throws UnirestException {
        GetStockFromAPI stockGetter = new GetStockFromAPI();
        this.stock = stockGetter.getStockBySymbolAsJSON(symbol);
    }

    public double getRawCurrentPrice(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            json_stock = json_stock.getJSONObject("financialData").getJSONObject("currentPrice");
            return json_stock.getDouble("raw");
        }
        else {
            logFail();
            //EXCEPTION;
            return 0;
        }
    }

    public String getSymbol(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getString("symbol");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    public String getName(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("longName");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    public boolean isMarketOpen(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            String status = json_stock.getJSONObject("price").getString("marketState");
            //return status.equals("OPEN");
            return status.equals("REGULAR");
        }
        else {
            logFail();
            //EXCEPTION;
            return false;
        }
    }

    public String getCurrency(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("currency");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    public String getRecommendationKey(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("financialData").getString("recommendationKey");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    public String getExchangeName(){
        //get the name of the stock exchange
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("exchangeName");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    public String getCurrencySymbol(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("currencySymbol");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    public String getDescription(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("summaryProfile").getString("longBusinessSummary");
        }
        else {
            logFail();
            //EXCEPTION;
            return null;
        }
    }

    private void logFail(){
        logger.warn("YahooFinanceAPI failed to load data, stock not initialized");
    }
}
