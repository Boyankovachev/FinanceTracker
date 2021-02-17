package com.diplomna.api.stock;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class YahooFinanceAPI {

    @Value("${rapidapiKey}")
    private String rapidapiKey;
    @Value("${rapidapiHost}")
    private String rapidapiHost;
    @Value("${rapidapiValue}")
    private String rapidapiKeyValue;
    private String rapidapiHostValue;

    private HttpResponse<JsonNode> stock;
    private final Logger logger;

    public YahooFinanceAPI() {
        rapidapiHostValue = "apidojo-yahoo-finance-v1.p.rapidapi.com";

        logger= LoggerFactory.getLogger(YahooFinanceAPI.class);
    }
    public YahooFinanceAPI(String symbol) throws UnirestException {
        rapidapiHostValue = "apidojo-yahoo-finance-v1.p.rapidapi.com";

        //return HttpResponse<JsonNode> selected by stock symbol
        //GET stock/get-detail
        //YahooFinanceAPI (RapidAPI)
        stock = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?symbol=" + symbol)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();

        logger= LoggerFactory.getLogger(YahooFinanceAPI.class);
    }

    public void setStockBySymbol(String symbol) throws UnirestException {
        //set stock object to a specific stock
        this.stock = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?symbol=" + symbol)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
    }

    public double getRawCurrentPrice(){
        //return stock current price
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
        //return stock symbol
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getString("symbol");
        }
        else {
            logFail();
            return null;
        }
    }

    public String getName(){
        //return stock name
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
        //return if market is open
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
        //return currency
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
        //return recommendation key
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
        //return exchange name
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("exchangeName");
        }
        else {
            logFail();
            return null;
        }
    }

    public String getCurrencySymbol(){
        //return currency symbol
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
        //return description
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
