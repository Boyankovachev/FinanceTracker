package com.diplomna.api.alphavantage;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class AlphaVantageAPI {

    @Value("${rapidapiKey}")
    private String rapidapiKey;
    @Value("${rapidapiHost}")
    private String rapidapiHost;
    @Value("${rapidapiValue}")
    private String rapidapiKeyValue;
    private String rapidapiHostValue;

    private HttpResponse<JsonNode> response = null;

    public AlphaVantageAPI(){
        rapidapiHostValue = "alpha-vantage.p.rapidapi.com";
    }

    public void setIndex(String symbol) throws UnirestException {
        //Set response object to desired index
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
    }

    public void setCrypto(String symbol) throws UnirestException {
        //Set response object to desired cryptocurrency
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?from_currency=" + symbol + "&function=CURRENCY_EXCHANGE_RATE&to_currency=USD")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
    }

    public JSONObject getStockTimeSeries(String symbol) throws UnirestException, JSONException {
        //Get stock historical data
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?symbol=" + symbol + "&function=TIME_SERIES_MONTHLY&datatype=json")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        return this.response.getBody().getObject().getJSONObject("Monthly Time Series");
    }

    public void setInitialIndex(String symbol) throws UnirestException {
        //Set response object to desired initial index
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?keywords=" + symbol + "&function=SYMBOL_SEARCH&datatype=json")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
    }

    public HashMap<String, String> getInitialIndex(){
        //return initial index information as a HashMap
        HashMap<String, String> info = new HashMap<>();
        info.put("name", response.getBody().getObject().getJSONArray("bestMatches").getJSONObject(0).getString("2. name"));
        info.put("currency", response.getBody().getObject().getJSONArray("bestMatches").getJSONObject(0).getString("8. currency"));
        return info;
    }

    public String getIndexPrice() throws JSONException{
        //return index current price as String
        return response.getBody().getObject().getJSONObject("Global Quote").getString("05. price");
    }

    public HashMap<String, String> getCrypto() throws JSONException {
        //return crypto information as a HashMap
        HashMap<String, String> info = new HashMap<>();
        info.put("name", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("2. From_Currency Name"));
        info.put("currency", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("4. To_Currency Name"));
        info.put("currencySymbol", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("3. To_Currency Code"));
        info.put("price", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("8. Bid Price"));
        return info;
    }
}


// https://alpha-vantage.p.rapidapi.com/query?symbol=MSFT&function=TIME_SERIES_MONTHLY&datatype=json - stock time series monthly