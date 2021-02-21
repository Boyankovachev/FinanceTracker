package com.diplomna.graph;

import java.util.ArrayList;
import java.util.List;

public class TestGraph {
    static public void main(String []args){

        //SLOJI GI V UNIT TESTOVE
        /*
        GraphInfoHolder holder = new GraphInfoHolder();
        holder.getDaily().add(new GraphInfo());
        System.out.println(holder.isDailyEmpty());
        System.out.println(holder.isWeeklyEmpty());
        System.out.println(holder.isMonthlyEmpty());

         */
        /*
        GraphInfoManager graphInfoManager = new GraphInfoManager();
        System.out.println(graphInfoManager.getCryptoInfoList().size());
        GraphInfoHolder graphInfoHolder = new GraphInfoHolder();
        graphInfoHolder.setAssetSymbol("ne BTC");
        graphInfoManager.getCryptoInfoList().add(graphInfoHolder);
        System.out.println(graphInfoManager.getCryptoInfoList().size());
        GraphInfoHolder BTC = new GraphInfoHolder();
        BTC.setAssetSymbol("BTC");
        System.out.println(BTC.isDailyEmpty());
        List<GraphInfo> graphInfo = new ArrayList<>();
        graphInfo.add(new GraphInfo());
        graphInfo.add(new GraphInfo());
        BTC.setDaily(graphInfo);
        graphInfoManager.getCryptoInfoList().add(BTC);
        System.out.println(graphInfoManager.getCryptoInfo("BTC").isDailyEmpty());
        System.out.println(graphInfoManager.getCryptoInfo("BTC").isMonthlyEmpty());
         */
        GraphInfoManager graphInfoManager = new GraphInfoManager();
        System.out.println(graphInfoManager.getStockInfo("TSLA"));
        System.out.println(graphInfoManager.getCryptoInfo("BTC"));
        System.out.println(graphInfoManager.getIndexInfo("SPY"));
    }
}
