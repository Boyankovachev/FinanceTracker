package com.diplomna.api.alphavantage;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AlphaVantageAPI {
    final private String rapidapiKey;
    final private String rapidapiHost;
    final private String rapidapiKeyValue;
    final private String rapidapiHostValue;

    private HttpResponse<JsonNode> response = null;

    public AlphaVantageAPI(){
        rapidapiKey = "x-rapidapi-key";
        rapidapiHost = "x-rapidapi-host";
        rapidapiKeyValue = "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493";
        rapidapiHostValue = "alpha-vantage.p.rapidapi.com";
    }

    public void setIndex(String symbol) throws UnirestException {
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol)
                .header("x-rapidapi-key", "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493")
                .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                .asJson();
    }

    public void setCrypto(String symbol) throws UnirestException {
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?from_currency=" + symbol + "&function=CURRENCY_EXCHANGE_RATE&to_currency=USD")
                .header("x-rapidapi-key", "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493")
                .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                .asJson();
    }

    public JSONObject getStockTimeSeries(String symbol) throws UnirestException, JSONException {
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?symbol=" + symbol + "&function=TIME_SERIES_MONTHLY&datatype=json")
                .header("x-rapidapi-key", "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493")
                .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                .asJson();
        return this.response.getBody().getObject().getJSONObject("Monthly Time Series");
    }

    public void setInitialIndex(String symbol) throws UnirestException {
        this.response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?keywords=" + symbol + "&function=SYMBOL_SEARCH&datatype=json")
                .header("x-rapidapi-key", "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493")
                .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                .asJson();
    }

    public HashMap<String, String> getInitialIndex(){
        HashMap<String, String> info = new HashMap<>();
        info.put("name", response.getBody().getObject().getJSONArray("bestMatches").getJSONObject(0).getString("2. name"));
        info.put("currency", response.getBody().getObject().getJSONArray("bestMatches").getJSONObject(0).getString("8. currency"));
        return info;
    }

    public String getIndexPrice() throws JSONException{
        return response.getBody().getObject().getJSONObject("Global Quote").getString("05. price");
    }

    public HashMap<String, String> getCrypto() throws JSONException {
        HashMap<String, String> info = new HashMap<>();
        info.put("name", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("2. From_Currency Name"));
        info.put("currency", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("4. To_Currency Name"));
        info.put("currencySymbol", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("3. To_Currency Code"));
        info.put("price", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("8. Bid Price"));
        return info;
    }
}


// https://alpha-vantage.p.rapidapi.com/query?symbol=MSFT&function=TIME_SERIES_MONTHLY&datatype=json - stock time series monthly