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

    private final String rapidapiKey;
    private final String rapidapiHost;
    private final String rapidapiKeyValue;
    private final String rapidapiHostValue;

    public AlphaVantageAPI(){
        rapidapiHostValue = "alpha-vantage.p.rapidapi.com";
        rapidapiKey = "x-rapidapi-key";
        rapidapiHost = "x-rapidapi-host";
        rapidapiKeyValue = "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493";
    }

    public JsonNode getStockAndIndexTimeSeries(String symbol, String period) throws UnirestException, JSONException {
        //Get stock historical data
        HttpResponse<JsonNode> response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?symbol=" + symbol + "&function=TIME_SERIES_" + period + "_ADJUSTED&datatype=json")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        return response.getBody();
    }

    public JsonNode getCryptoTimeSeries(String symbol, String period) throws UnirestException, JSONException {
        //Get stock historical data
        HttpResponse<JsonNode> response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?market=USD&symbol=" + symbol + "&function=DIGITAL_CURRENCY_" + period)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        return response.getBody();
    }

    public HashMap<String, String> getInitialIndex(String symbol) throws JSONException, UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?keywords=" + symbol + "&function=SYMBOL_SEARCH&datatype=json")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        //return initial index information as a HashMap

        HashMap<String, String> info = new HashMap<>();
        info.put("name", response.getBody().getObject().getJSONArray("bestMatches").getJSONObject(0).getString("2. name"));
        info.put("currency", response.getBody().getObject().getJSONArray("bestMatches").getJSONObject(0).getString("8. currency"));
        return info;
    }

    public String getIndexPrice(String symbol) throws JSONException, UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol)
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        //return index current price as String
        return response.getBody().getObject().getJSONObject("Global Quote").getString("05. price");
    }

    public HashMap<String, String> getCrypto(String symbol) throws JSONException, UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://alpha-vantage.p.rapidapi.com/query?from_currency=" + symbol + "&function=CURRENCY_EXCHANGE_RATE&to_currency=USD")
                .header(rapidapiKey, rapidapiKeyValue)
                .header(rapidapiHost, rapidapiHostValue)
                .asJson();
        //return crypto information as a HashMap
        HashMap<String, String> info = new HashMap<>();
        info.put("name", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("2. From_Currency Name"));
        info.put("currency", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("4. To_Currency Name"));
        info.put("currencySymbol", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("3. To_Currency Code"));
        info.put("price", response.getBody().getObject().getJSONObject("Realtime Currency Exchange Rate").getString("8. Bid Price"));
        return info;
    }
}
