package com.diplomna.api.stock;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class ParseStock {
    private HttpResponse<JsonNode> stock;
    public ParseStock() {}
    public ParseStock(String symbol) throws UnirestException {
        GetStockFromAPI stockGetter = new GetStockFromAPI();
        stock = stockGetter.getStockBySymbolAsJSON(symbol);
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
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return 0;
        }
    }

    public String getSymbol(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getString("symbol");
        }
        else {
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return null;
        }
    }

    public String getName(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("longName");
        }
        else {
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
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
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return false;
        }
    }

    public String getCurrency(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("currency");
        }
        else {
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return null;
        }
    }

    public String getRecommendationKey(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("financialData").getString("recommendationKey");
        }
        else {
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
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
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return null;
        }
    }

    public String getCurrencySymbol(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("price").getString("currencySymbol");
        }
        else {
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return null;
        }
    }

    public String getDescription(){
        if(stock!=null) {
            JSONObject json_stock = stock.getBody().getObject();
            return json_stock.getJSONObject("summaryProfile").getString("longBusinessSummary");
        }
        else {
            // NQMA STOCK NQKUV EXCEPTION I LOGVANE
            System.out.println("stock == null");
            return null;
        }
    }
}
