package com.diplomna.api.alphavantage;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class AlphaVantageAPI {

    private String rapidapiKey;
    private String rapidapiHost;
    private String rapidapiKeyValue;
    private String rapidapiHostValue;

    private HttpResponse<JsonNode> response = null;

    public AlphaVantageAPI(){
        rapidapiHostValue = "alpha-vantage.p.rapidapi.com";
        rapidapiKey = "x-rapidapi-key";
        rapidapiHost = "x-rapidapi-host";
        rapidapiKeyValue = "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493";
    }

    public void setIndex(String symbol) throws UnirestException {
        //Set response object to desired index
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
    }
    public void setInitialIndex(String symbol) throws UnirestException {
        //Set response object to desired initial index
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?keywords=" + symbol + "&function=SYMBOL_SEARCH&datatype=json")
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

    public JsonNode getStockAndIndexTimeSeries(String symbol, String period) throws UnirestException, JSONException {
        //Get stock historical data
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?symbol=" + symbol + "&function=TIME_SERIES_" + period + "_ADJUSTED&datatype=json")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        return this.response.getBody();
    }

    public JsonNode getCryptoTimeSeries(String symbol, String period) throws UnirestException, JSONException {
        //Get stock historical data
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?market=USD&symbol=" + symbol + "&function=DIGITAL_CURRENCY_" + period)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        return this.response.getBody();
    }


    public HashMap<String, String> getInitialIndex() throws JSONException{
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


// https://alpha-vantage.p.rapidapi.com/query?symbol=MSFT&function=TIME_SERIES_MONTHLY&datatype=json - stock time series monthly AND INDEX TOO
// https://alpha-vantage.p.rapidapi.com/query?symbol=MSFT&function=TIME_SERIES_MONTHLY_ADJUSTED&datatype=json - stock time series monthly ADJUSTED AND INDEX TOO
// https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=MSFT&outputsize=compact&datatype=json
// https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_WEEKLY_ADJUSTED&symbol=MSFT&datatype=json

//crypto -   daily "https://alpha-vantage.p.rapidapi.com/query?market=USD&symbol=BTC&function=DIGITAL_CURRENCY_DAILY"
//crypto -  weekly "https://alpha-vantage.p.rapidapi.com/query?function=DIGITAL_CURRENCY_WEEKLY&symbol=BTC&market=USD"
//crypto - monthly "https://alpha-vantage.p.rapidapi.com/query?market=CNY&function=DIGITAL_CURRENCY_MONTHLY&symbol=BTC"
//Razlikata e DIGITAL_CURRENCY_DAILY/WEEKLY/MONTHLY
