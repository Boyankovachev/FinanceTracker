package com.diplomna.api.stock;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class GetStock {
    final private String rapidapiKey;
    final private String rapidapiHost;
    final private String rapidapiKeyValue;
    final private String rapidapiHostValue;
    public GetStock(){
        rapidapiKey = "x-rapidapi-key";
        rapidapiHost = "x-rapidapi-host";
        rapidapiKeyValue = "7d138e1390mshe8742377115d21fp1dd201jsnb3b741a37493";
        rapidapiHostValue = "apidojo-yahoo-finance-v1.p.rapidapi.com";
    }

    public HttpResponse<JsonNode> getStockBySymbolAsJSON(final String symbol){
        //return HttpResponse<JsonNode> selected by stock symbol
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?symbol=" + symbol)
                    .header(rapidapiKey, rapidapiKeyValue)
                    .header(rapidapiHost, rapidapiHostValue)
                    .asJson();
            return response;
        } catch (UnirestException e) {
            // log errors here
            e.printStackTrace();
        }
        return null;
    }
}



/*
GET merket/get-summary
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-summary?region=US"

GET market/v2/get-summary
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-summary?region=US"

GET merket/get-earnings
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-earnings?region=US&startDate=1585155600000&endDate=1589475600000&size=10"

GET merket/get-watchlist-detail
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-watchlist-detail?userId=X3NJ2A7VDSABUI4URBWME2PZNM&pfId=the_berkshire_hathaway_portfolio"

GET stock/get-detail
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?symbol=NBEV&region=US"

GET stock/v2/get-analysis
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-analysis?symbol=AMRN&region=US"

GET stock/v2/get-summary
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-summary?symbol=AMRN&region=US"

GET news/v2/get-details
"https://apidojo-yahoo-finance-v1.p.rapidapi.com/news/v2/get-details?uuid=9803606d-a324-3864-83a8-2bd621e6ccbd&region=US"
 */