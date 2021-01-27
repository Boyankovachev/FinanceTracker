package com.diplomna.assets;

import com.diplomna.assets.finished.PassiveResource;
import com.diplomna.assets.finished.Stock;
import com.diplomna.assets.sub.ActiveAsset;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.date.DatеManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseInfoTest {

    @Test
    public void testPurchaseInfoList(){
        // testing PurchaseInfo List getters
        DatеManager date = new DatеManager();
        PurchaseInfo pokupka1 = new PurchaseInfo(144.6,date,15.53);
        PurchaseInfo pokupka2 = new PurchaseInfo(14,date,12.1);
        PurchaseInfo pokupka3 = new PurchaseInfo(251,date,13);
        PurchaseInfo pokupka4 = new PurchaseInfo(2.5,date,2.6);
        ActiveAsset asset = new ActiveAsset();
        asset.addPurchase(pokupka1);
        asset.addPurchase(pokupka2);
        asset.addPurchase(pokupka3);
        asset.addPurchase(pokupka4);
        assertEquals(asset.getPurchaseByIndex(3).getPrice(), 2.5);
        assertEquals(asset.getPurchaseByPrice(251).getQuantity(), 13);
        assertEquals(asset.getPurchaseByQuantity(2.6).getPrice(), 2.5);
    }

    @Test
    public void testPercentChangeCalculators(){
        // testing percent change
        PassiveResource passiveResource = new PassiveResource();
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPrice(125);
        passiveResource.setPurchaseInfo(purchaseInfo);
        passiveResource.setCurrentMarketPrice(150);
        passiveResource.calculatePercentChange();
        assertEquals(passiveResource.getPercentChange(), 20);

        Stock stock = new Stock();
        stock.setCurrentMarketPrice(123);
        stock.setAveragePurchasePrice(224);
        stock.calculatePercentChange();
        assertEquals(stock.getPercentChange(), -45.089285714285715);
    }

}
