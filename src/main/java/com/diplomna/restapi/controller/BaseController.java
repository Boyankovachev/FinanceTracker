package com.diplomna.restapi.controller;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.date.DatеManager;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.sub.User;
import com.mashape.unirest.http.JsonNode;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private BaseService baseService;

    private User user;

    public BaseController(){
        baseService = new BaseService();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserHomepage(@ModelAttribute("user") User user, Model model){
        if(user.getUserName() == null){
            return "redirect:/";
        }
        this.user = baseService.setupUser(user);
        model.addAttribute("stock", user.getAssets().getAllStocks());
        model.addAttribute("passive_resource", user.getAssets().getAllPassiveResources());
        model.addAttribute("index", user.getAssets().getAllIndex());
        model.addAttribute("crypto", user.getAssets().getCrypto());
        model.addAttribute("commodity", user.getAssets().getCommodities());
        model.addAttribute("user", user);
        return "user-homepage";
        //return "test";
    }

    @RequestMapping(value = "/notification", method = RequestMethod.GET)
    public String notification(Model model){
        if(user == null){
            return "redirect:/";
        }
        else {
            model.addAttribute("notification", user.getNotifications());
            return "notification";
        }
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String settings(Model model){
        if(user == null){
            return "redirect:/";
        }
        else {
            model.addAttribute("user", user);
            return "settings";
        }
    }

    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    public RedirectView assets(@RequestParam("asset_type") String assetType, @RequestParam("asset_name") String assetName, RedirectAttributes attributes){
        attributes.addFlashAttribute("assetName", assetName);
        attributes.addFlashAttribute("assetType", assetType);
        return new RedirectView("asset");
    }
    @RequestMapping(value = "/asset", method = RequestMethod.GET)
    public String assets(@ModelAttribute("assetName") String assetName, @ModelAttribute("assetType") String assetType, Model model){
        if(user == null){
            return "redirect:/";
        }
        else {
            try {
                // Sleep to avoid post from javascript and get from html getting de-synchronised
                Thread.sleep(30);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            switch (assetType) {
                case "stock":
                    if(user.getAssets().getStockByName(assetName) == null){
                        //exception i logvane
                        return "";
                    }
                    else{
                        Stock stock = user.getAssets().getStockByName(assetName);
                        model.addAttribute("stock", stock);
                        model.addAttribute("purchase", stock.getAllPurchases());
                        return "assets/stock";
                    }
                case "passive-resource":
                    if(user.getAssets().getPassiveResourceByName(assetName) == null){
                        //exception i logvane
                        return "";
                    }
                    else{
                        PassiveResource passiveResource = user.getAssets().getPassiveResourceByName(assetName);
                        model.addAttribute("passive_resource", passiveResource);
                        return "assets/passive-resource";
                    }
                case "index":
                    if(user.getAssets().getIndexByName(assetName) == null){
                        //exception i logvane
                        return "";
                    }
                    else{
                        Index index = user.getAssets().getIndexByName(assetName);
                        model.addAttribute("index", index);
                        model.addAttribute("purchase", index.getAllPurchases());
                        return "assets/index";
                    }
                case "crypto":
                    if(user.getAssets().getCryptoByName(assetName) == null){
                        //exception i logvane
                        return "";
                    }
                    else{
                        Crypto crypto = user.getAssets().getCryptoByName(assetName);
                        model.addAttribute("crypto", crypto);
                        model.addAttribute("purchase", crypto.getAllPurchases());
                        return "assets/crypto";
                    }
                case "commodity":
                    if(user.getAssets().getCommodityByName(assetName) == null){
                        //exception i logvane
                        return "";
                    }
                    else{
                        Commodities commodity = user.getAssets().getCommodityByName(assetName);
                        model.addAttribute("commodity", commodity);
                        model.addAttribute("purchase", commodity.getAllPurchases());
                        return "assets/commodity";
                    }
                default:
                    //tuka exception i logvane (ne trqbva da vliza tuka)
                    // tva e za da izbegna shibanata greska
                    model.addAttribute("stock", user.getAssets().getAllStocks());
                    model.addAttribute("passive_resource", user.getAssets().getAllPassiveResources());
                    model.addAttribute("index", user.getAssets().getAllIndex());
                    model.addAttribute("crypto", user.getAssets().getCrypto());
                    model.addAttribute("commodity", user.getAssets().getCommodities());
                    model.addAttribute("user", user);
                    return "user-homepage";
            }
        }
    }

    @RequestMapping(value = "/add-purchase", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Object> addPurchase(
            @RequestParam("quantity") double quantity, @RequestParam("price") double price,
            @RequestParam(value = "date", required = false) String date, @RequestParam("asset_type") String assetType,
            @RequestParam("asset_name") String assetName, @RequestParam(value = "asset_symbol", required = false) String assetSymbol){

        HashMap<String, String> map = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        PurchaseInfo purchaseInfo = new PurchaseInfo(price, quantity);
        if(date != null && !date.equals("")){
            try {
                DatеManager datеManager = new DatеManager();
                datеManager.setDateFromString(date);
                purchaseInfo.setPurchaseDate(datеManager);
                map.put("date", datеManager.getDateAsString());
            } catch (ParseException e) {
                //return "Wrong date format. Please use day.month.year!";
                map.put("success", "Wrong date format. Please use day.month.year!");
            }
        }
        map.put("price", String.valueOf(price));
        map.put("quantity", String.valueOf(quantity));

        InsertIntoDb insertIntoDb = new InsertIntoDb("test");

        switch (assetType){
            case "stock":
                if(assetSymbol != null && !assetSymbol.equals("")) {
                    purchaseInfo.setStockSymbol(assetSymbol);
                    insertIntoDb.insertStockPurchaseInfo(user.getUserId(), purchaseInfo);
                    map.put("success", "success");

                    //add new purchase to RAM
                    this.user.getAssets().addPurchaseToResource(assetType, assetSymbol, purchaseInfo);
                }
                else {
                    //return "An error has occurred! Purchase not registered.";
                    map.put("success", "An error has occurred! Purchase not registered.");
                    httpStatus = HttpStatus.EXPECTATION_FAILED;
                }
                break;
            case "index":
                if(assetSymbol != null && !assetSymbol.equals("")) {
                    purchaseInfo.setStockSymbol(assetSymbol);
                    insertIntoDb.insertIndexPurchaseInfo(user.getUserId(), purchaseInfo);
                    map.put("success", "success");

                    //add new purchase to RAM
                    this.user.getAssets().addPurchaseToResource(assetType, assetSymbol, purchaseInfo);
                }
                else {
                    map.put("success", "An error has occurred! Purchase not registered.");
                    httpStatus = HttpStatus.EXPECTATION_FAILED;
                }
                break;
            case "crypto":
                if(assetSymbol != null && !assetSymbol.equals("")) {
                    purchaseInfo.setStockSymbol(assetSymbol);
                    insertIntoDb.insertCryptoPurchaseInfo(user.getUserId(), purchaseInfo);
                    map.put("success", "success");

                    //add new purchase to RAM
                    this.user.getAssets().addPurchaseToResource(assetType, assetSymbol, purchaseInfo);
                }
                else {
                    map.put("success", "An error has occurred! Purchase not registered.");
                    httpStatus = HttpStatus.EXPECTATION_FAILED;
                }
                break;
            case "commodity":
                //commodities have no symbol and are referenced by name
                purchaseInfo.setStockSymbol(assetName);
                insertIntoDb.insertCommodityPurchaseInfo(user.getUserId(), purchaseInfo);
                map.put("success", "success");

                //add new purchase to RAM
                this.user.getAssets().addPurchaseToResource(assetType, assetName, purchaseInfo);
                
                break;
            default:
                //tuka exception i logvane (ne trqbva da vliza tuka)
                map.put("success", "An error has occurred! Purchase not registered.");
                break;
        }

        return new ResponseEntity<Object>(map, httpStatus);
    }

    @RequestMapping(value = "/add-asset", method = RequestMethod.GET)
    public String addAsset(){
        if(user == null){
            return "redirect:/";
        }
        return "add-asset";
    }

    @RequestMapping(value = "/add-active-asset", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> addActiveAsset(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String response = baseService.addAsset(jsonObject, user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("response", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }
    @RequestMapping(value = "/add-passive-asset", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> addPassiveAsset(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String response = baseService.addPassiveAsset(jsonObject, user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("response", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

}
