package com.diplomna.graph;

import com.diplomna.api.alphavantage.AlphaVantageAPI;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.singleton.CurrentData;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GraphService {

    private final Logger logger;

    public GraphService(){
        this.logger = LoggerFactory.getLogger(GraphService.class);
    }

    public List<GraphInfo> getChartData(JSONObject jsonObject){
        /*
            Returns chart data for asset
            If data is missing in RAM,
            fetch from API, add to RAM and then return
         */
        CurrentData currentData = CurrentData.getInstance();
        String symbol = jsonObject.getString("symbol");
        switch (jsonObject.getString("type")){
            case "Stock":
                switch (jsonObject.getString("period")){
                    case "daily":
                        if(currentData.getGraphInfoManager().isStockPresent(symbol)){
                            if(currentData.getGraphInfoManager().getStockInfo(symbol).isDailyEmpty()){
                                //if stock exists but daily is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "daily");
                                currentData.getGraphInfoManager().getStockInfo(symbol).setDaily(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getStockInfo(symbol).getDaily();
                            }
                        }else {
                            //if stock is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "daily");
                            graphInfoHolder.setDaily(temp);
                            currentData.getGraphInfoManager().getStockInfoList().add(graphInfoHolder);
                            return temp;
                        }
                    case "weekly":
                        if(currentData.getGraphInfoManager().isStockPresent(symbol)){
                            if(currentData.getGraphInfoManager().getStockInfo(symbol).isWeeklyEmpty()){
                                //if weekly is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "weekly");
                                currentData.getGraphInfoManager().getStockInfo(symbol).setWeekly(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getStockInfo(symbol).getWeekly();
                            }
                        }else {
                            //if stock is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "weekly");
                            graphInfoHolder.setWeekly(temp);
                            currentData.getGraphInfoManager().getStockInfoList().add(graphInfoHolder);
                            return temp;
                        }
                    case "monthly":
                        if(currentData.getGraphInfoManager().isStockPresent(symbol)){
                            if(currentData.getGraphInfoManager().getStockInfo(symbol).isMonthlyEmpty()){
                                //if monthly is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "monthly");
                                currentData.getGraphInfoManager().getStockInfo(symbol).setMonthly(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getStockInfo(symbol).getMonthly();
                            }
                        }else {
                            //if stock is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "monthly");
                            graphInfoHolder.setMonthly(temp);
                            currentData.getGraphInfoManager().getStockInfoList().add(graphInfoHolder);
                            return temp;
                        }
                }
                break;
            case "Index":
                switch (jsonObject.getString("period")){
                    case "daily":
                        if(currentData.getGraphInfoManager().isIndexPresent(symbol)){
                            if(currentData.getGraphInfoManager().getIndexInfo(symbol).isDailyEmpty()){
                                //if index exists but daily is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "daily");
                                currentData.getGraphInfoManager().getIndexInfo(symbol).setDaily(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getIndexInfo(symbol).getDaily();
                            }
                        }else {
                            //if index is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "daily");
                            graphInfoHolder.setDaily(temp);
                            currentData.getGraphInfoManager().getIndexInfoList().add(graphInfoHolder);
                            return temp;
                        }
                    case "weekly":
                        if(currentData.getGraphInfoManager().isCryptoPresent(symbol)){
                            if(currentData.getGraphInfoManager().getIndexInfo(symbol).isWeeklyEmpty()){
                                //if weekly is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "weekly");
                                currentData.getGraphInfoManager().getIndexInfo(symbol).setWeekly(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getIndexInfo(symbol).getWeekly();
                            }
                        }else {
                            //if index is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "weekly");
                            graphInfoHolder.setWeekly(temp);
                            currentData.getGraphInfoManager().getIndexInfoList().add(graphInfoHolder);
                            return temp;
                        }
                    case "monthly":
                        if(currentData.getGraphInfoManager().isCryptoPresent(symbol)){
                            if(currentData.getGraphInfoManager().getIndexInfo(symbol).isMonthlyEmpty()){
                                //if monthly is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "monthly");
                                currentData.getGraphInfoManager().getIndexInfo(symbol).setMonthly(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getIndexInfo(symbol).getMonthly();
                            }
                        }else {
                            //if index is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getStockAndIndexGraphInfo(symbol, "monthly");
                            graphInfoHolder.setMonthly(temp);
                            currentData.getGraphInfoManager().getIndexInfoList().add(graphInfoHolder);
                            return temp;
                        }
                }
                break;
            case "Crypto":
                switch (jsonObject.getString("period")){
                    case "daily":
                        if(currentData.getGraphInfoManager().isCryptoPresent(symbol)){
                            if(currentData.getGraphInfoManager().getCryptoInfo(symbol).isDailyEmpty()){
                                //if crypto exists but daily is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getCryptoGraphInfo(symbol, "daily");
                                currentData.getGraphInfoManager().getCryptoInfo(symbol).setDaily(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getCryptoInfo(symbol).getDaily();
                            }
                        }else {
                            //if crypto is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getCryptoGraphInfo(symbol, "daily");
                            graphInfoHolder.setDaily(temp);
                            currentData.getGraphInfoManager().getCryptoInfoList().add(graphInfoHolder);
                            return temp;
                        }
                    case "weekly":
                        if(currentData.getGraphInfoManager().isCryptoPresent(symbol)){
                            if(currentData.getGraphInfoManager().getCryptoInfo(symbol).isWeeklyEmpty()){
                                //if crypto is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getCryptoGraphInfo(symbol, "weekly");
                                currentData.getGraphInfoManager().getCryptoInfo(symbol).setWeekly(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getCryptoInfo(symbol).getWeekly();
                            }
                        }else {
                            //if crypto is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getCryptoGraphInfo(symbol, "weekly");
                            graphInfoHolder.setWeekly(temp);
                            currentData.getGraphInfoManager().getCryptoInfoList().add(graphInfoHolder);
                            return temp;
                        }
                    case "monthly":
                        if(currentData.getGraphInfoManager().isCryptoPresent(symbol)){
                            if(currentData.getGraphInfoManager().getCryptoInfo(symbol).isMonthlyEmpty()){
                                //if monthly is empty
                                //fetch from API, Add to RAM and return
                                List<GraphInfo> temp = getCryptoGraphInfo(symbol, "monthly");
                                currentData.getGraphInfoManager().getCryptoInfo(symbol).setMonthly(temp);
                                return temp;
                            }
                            else {
                                return currentData.getGraphInfoManager().getCryptoInfo(symbol).getMonthly();
                            }
                        }else {
                            //if crypto is empty
                            //fetch from API, Add to RAM and return
                            GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
                            graphInfoHolder.setAssetSymbol(symbol);
                            List<GraphInfo> temp = getCryptoGraphInfo(symbol, "monthly");
                            graphInfoHolder.setMonthly(temp);
                            currentData.getGraphInfoManager().getCryptoInfoList().add(graphInfoHolder);
                            return temp;
                        }
                }
                break;
        }
        return null;
    }

    private List<GraphInfo> getStockAndIndexGraphInfo(String symbol, String timePeriod){
        /*
            Return stock and index historical data list
         */
        List<GraphInfo> graphInfoList = new ArrayList<>();
        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        try {
            JsonNode jsonNode = alphaVantageAPI.getStockAndIndexTimeSeries(symbol, timePeriod.toUpperCase());
            JSONObject jsonObject;
            if(timePeriod.equals("monthly")) {
                jsonObject = jsonNode.getObject().getJSONObject("Monthly Adjusted Time Series");
            }
            else if(timePeriod.equals("daily")){
                jsonObject = jsonNode.getObject().getJSONObject("Time Series (Daily)");
            }
            else if(timePeriod.equals("weekly")) {
                jsonObject = jsonNode.getObject().getJSONObject("Weekly Adjusted Time Series");
            }
            else {
                return null;
            }

            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) { // iterating through JSONObject
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    String[] temp = key.split("-");
                    GraphInfo graphInfo = new GraphInfo(Integer.parseInt(temp[2]),
                            Integer.parseInt(temp[1]), Integer.parseInt(temp[0]),
                            Double.parseDouble(jsonObject.getJSONObject(key).get("5. adjusted close").toString()));

                    graphInfoList.add(graphInfo);
                }
            }

            //api provides data mixed up
            //sort it
            sortGraphInfo(graphInfoList);

            return graphInfoList;
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
            String errorMessage = "AlphaVantage api stock and index fail historical data: "
                    + symbol + "\n" + e.getMessage();
            logger.error(errorMessage);
            return null;
        }
    }

    private List<GraphInfo> getCryptoGraphInfo(String symbol, String timePeriod){
        /*
            Return crypto historical data list
         */
        List<GraphInfo> graphInfoList = new ArrayList<>();
        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        try {
            JsonNode jsonNode = alphaVantageAPI.getCryptoTimeSeries(symbol, timePeriod.toUpperCase());
            JSONObject jsonObject;
            if(timePeriod.equals("monthly")) {
                jsonObject = jsonNode.getObject().getJSONObject("Time Series (Digital Currency Monthly)");
            }
            else if(timePeriod.equals("daily")){
                jsonObject = jsonNode.getObject().getJSONObject("Time Series (Digital Currency Daily)");
            }
            else if(timePeriod.equals("weekly")) {
                jsonObject = jsonNode.getObject().getJSONObject("Time Series (Digital Currency Weekly)");
            }
            else {
                return null;
            }

            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) { // iterating through JSONObject
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    String[] temp = key.split("-");
                    GraphInfo graphInfo = new GraphInfo(Integer.parseInt(temp[2]),
                            Integer.parseInt(temp[1]), Integer.parseInt(temp[0]),
                            Double.parseDouble(jsonObject.getJSONObject(key).get("4b. close (USD)").toString()));

                    graphInfoList.add(graphInfo);
                }
            }

            //api provides data mixed up
            //sort it
            sortGraphInfo(graphInfoList);

            return graphInfoList;
        } catch (UnirestException e) {
            e.printStackTrace();
            String errorMessage = "AlphaVantage api crypto fail historical data: " + symbol;
            logger.error(errorMessage);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            String errorMessage = "AlphaVantage api crypto json fail historical data: " + symbol;
            logger.error(errorMessage);
        }
        return null;
    }

    private void sortGraphInfo(List<GraphInfo> graphInfoList){
        //Sorting needs to be done for year, month and dat
        for(int j=0; j<graphInfoList.size()-1; j++) {
            for (int i = 0; i < graphInfoList.size() - 1; i++) {
                if (graphInfoList.get(i).getYear() > graphInfoList.get(i + 1).getYear()){
                    swapGraphInfoList(graphInfoList, i);
                }
            }
        }
        for(int j=0; j<graphInfoList.size()-1; j++) {
            for (int i = 0; i < graphInfoList.size() - 1; i++) {
                if (graphInfoList.get(i).getMonth() > graphInfoList.get(i + 1).getMonth()){
                    if(graphInfoList.get(i).getYear() >= graphInfoList.get(i+1).getYear()) {
                        swapGraphInfoList(graphInfoList, i);
                    }
                }
            }
        }
        for(int j=0; j<graphInfoList.size()-1; j++) {
            for (int i = 0; i < graphInfoList.size() - 1; i++) {
                if (graphInfoList.get(i).getDay() > graphInfoList.get(i + 1).getDay()){
                    if(graphInfoList.get(i).getMonth() >= graphInfoList.get(i + 1).getMonth()
                        && graphInfoList.get(i).getYear() >= graphInfoList.get(i+1).getYear()) {
                        swapGraphInfoList(graphInfoList, i);
                    }
                }
            }
        }
    }

    private void swapGraphInfoList(List<GraphInfo> graphInfoList, int i){
        /*
            swap objects in graph info list
            from i and i + 1
            used in getStockGraphInfo
         */
        GraphInfo temp = graphInfoList.get(i);
        graphInfoList.set(i, graphInfoList.get(i+1));
        graphInfoList.set(i+1, temp);
    }

}
