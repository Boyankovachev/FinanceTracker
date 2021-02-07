package com.diplomna.restapi.controller;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.*;
import com.diplomna.assets.sub.PurchaseInfo;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.date.DatеManager;
import com.diplomna.pojo.GraphInfo;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.sub.User;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mashape.unirest.http.JsonNode;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.ls.LSException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
        if(user.getUserName() == null && this.user == null){
            return "redirect:/";
        }
        else if(user.getUserName() != null && this.user == null){
            this.user = baseService.setupUser(user);
        }
        model.addAttribute("stock", this.user.getAssets().getAllStocks());
        model.addAttribute("passive_resource", this.user.getAssets().getAllPassiveResources());
        model.addAttribute("index", this.user.getAssets().getAllIndex());
        model.addAttribute("crypto", this.user.getAssets().getCrypto());
        model.addAttribute("commodity", this.user.getAssets().getCommodities());
        model.addAttribute("user", this.user);
        return "user-homepage";
    }

    @RequestMapping(value = "/notification", method = RequestMethod.GET)
    public String notification(Model model){
        if(user == null){
            return "redirect:/";
        }
        else {
            model.addAttribute("notification", user.getNotifications());
            model.addAttribute("stock", user.getAssets().getAllStocks());
            model.addAttribute("index", user.getAssets().getAllIndex());
            model.addAttribute("crypto", user.getAssets().getCrypto());
            model.addAttribute("commodity", user.getAssets().getCommodities());
            model.addAttribute("global",  baseService.getGlobalNotifications(user));
            return "notification";
        }
    }
    @RequestMapping(value = "/add-notification", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> addNotification(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String success = baseService.addNotification(jsonObject, user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("success", success);
        responseMap.put("price", String.valueOf(jsonObject.getDouble("priceTarget")));
        responseMap.put("name", jsonObject.getString("name"));
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
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
    @RequestMapping(value = "/change-2fa", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> change2FA(@RequestBody String value){
        HashMap<String, String> responseMap = new HashMap<>();
        String response = baseService.change2FA(value, user);
        responseMap.put("response", String.valueOf(user.getIs2FactorAuthenticationRequired()));
        responseMap.put("success", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }
    @RequestMapping(value = "/change-username", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> changeUsername(@RequestBody String value){
        HashMap<String, String> responseMap = new HashMap<>();
        String response = baseService.changeUsername(value, user);
        responseMap.put("response", user.getUserName());
        responseMap.put("success", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }
    @RequestMapping(value = "/change-email", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> changeEmail(@RequestBody String value){
        HashMap<String, String> responseMap = new HashMap<>();
        String response = baseService.changeEmail(value, user);
        responseMap.put("response", user.getEmail());
        responseMap.put("success", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> changePassword(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String response = baseService.changePassword(jsonObject, user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("response", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
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

    @RequestMapping(value = "/get-stock-data", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> getStockData(@RequestBody String stockSymbol){
        System.out.println(stockSymbol);
        List<GraphInfo> graphInfoList = baseService.getStockGraphInfo(stockSymbol);
        return new ResponseEntity<Object>(graphInfoList ,HttpStatus.OK);
    }

    @RequestMapping(value = "/add-purchase", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Object> addPurchase(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        HashMap<String, String> map = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        PurchaseInfo purchaseInfo = new PurchaseInfo(jsonObject.getDouble("price"), jsonObject.getDouble("quantity"));
        if(jsonObject.getString("date") != null && !jsonObject.getString("date").equals("")){
            try {
                DatеManager datеManager = new DatеManager();
                datеManager.setDateFromString(jsonObject.getString("date"));
                purchaseInfo.setPurchaseDate(datеManager);
                map.put("date", datеManager.getDateAsString());
            } catch (ParseException e) {
                //return "Wrong date format. Please use day.month.year!";
                map.put("success", "Wrong date format. Please use day.month.year!");
            }
        }
        map.put("price", String.valueOf(jsonObject.getDouble("price")));
        map.put("quantity", String.valueOf(jsonObject.getDouble("quantity")));

        InsertIntoDb insertIntoDb = new InsertIntoDb("test");

        switch (jsonObject.getString("assetType")){
            case "stock":
                if(jsonObject.getString("assetSymbol") != null && !jsonObject.getString("assetSymbol").equals("")) {
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetSymbol"));
                    insertIntoDb.insertStockPurchaseInfo(user.getUserId(), purchaseInfo);
                    map.put("success", "success");

                    //add new purchase to RAM
                    this.user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetSymbol"), purchaseInfo);
                }
                else {
                    //return "An error has occurred! Purchase not registered.";
                    map.put("success", "An error has occurred! Purchase not registered.");
                    httpStatus = HttpStatus.EXPECTATION_FAILED;
                }
                break;
            case "index":
                if(jsonObject.getString("assetSymbol") != null && !jsonObject.getString("assetSymbol").equals("")) {
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetSymbol"));
                    insertIntoDb.insertIndexPurchaseInfo(user.getUserId(), purchaseInfo);
                    map.put("success", "success");

                    //add new purchase to RAM
                    this.user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetSymbol"), purchaseInfo);
                }
                else {
                    map.put("success", "An error has occurred! Purchase not registered.");
                    httpStatus = HttpStatus.EXPECTATION_FAILED;
                }
                break;
            case "crypto":
                if(jsonObject.getString("assetSymbol") != null && !jsonObject.getString("assetSymbol").equals("")) {
                    purchaseInfo.setStockSymbol(jsonObject.getString("assetSymbol"));
                    insertIntoDb.insertCryptoPurchaseInfo(user.getUserId(), purchaseInfo);
                    map.put("success", "success");

                    //add new purchase to RAM
                    this.user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetSymbol"), purchaseInfo);
                }
                else {
                    map.put("success", "An error has occurred! Purchase not registered.");
                    httpStatus = HttpStatus.EXPECTATION_FAILED;
                }
                break;
            case "commodity":
                //commodities have no symbol and are referenced by name
                purchaseInfo.setStockSymbol(jsonObject.getString("assetName"));
                insertIntoDb.insertCommodityPurchaseInfo(user.getUserId(), purchaseInfo);
                map.put("success", "success");

                //add new purchase to RAM
                this.user.getAssets().addPurchaseToResource(jsonObject.getString("assetType"), jsonObject.getString("assetName"), purchaseInfo);
                
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

    @RequestMapping(value = "/log-out", method = RequestMethod.GET)
    public String logOut(){
        this.user = null;
        return "redirect:/";
    }

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String getHeader(){
        return "parts/header";
    }
    @RequestMapping(value = "/footer", method = RequestMethod.GET)
    public String getFooter(){
        return "parts/footer";
    }


    @RequestMapping(value = "/remove-asset", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Object> removeAsset(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String response = baseService.removeAsset(jsonObject, user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("response", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-passive-resource", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Object> changeCurrentPrice(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String response = baseService.changePassiveResourcePrice(jsonObject, user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("success", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove-notification", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Object> removeNotification(@RequestBody String jsonString){
        String response = baseService.removeNotification(new JSONObject(jsonString).getString("notificationName"), user);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("response", response);
        return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
    }

}
