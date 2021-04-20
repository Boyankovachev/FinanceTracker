package com.diplomna.restapi.controller;

import com.diplomna.assets.finished.*;
import com.diplomna.graph.GraphInfo;
import com.diplomna.graph.GraphService;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.restapi.service.LoginAndRegisterService;
import com.diplomna.users.sub.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private LoginAndRegisterService loginAndRegisterService;

    private User user;

    private final Logger logger;

    public BaseController(){
        baseService = new BaseService();
        this.logger = LoggerFactory.getLogger(BaseController.class);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserHomepage(Principal principal, Model model){ ;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VERIFY"))) {
            return "redirect:/confirm-login";
        }
        this.user = null;
        this.user = baseService.setupUser(loginAndRegisterService.getUserByEmail(principal.getName()));
        //user.printUser();
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
        model.addAttribute("notification", user.getNotifications());
        model.addAttribute("stock", user.getAssets().getAllStocks());
        model.addAttribute("index", user.getAssets().getAllIndex());
        model.addAttribute("crypto", user.getAssets().getCrypto());
        model.addAttribute("commodity", user.getAssets().getCommodities());
        model.addAttribute("global",  baseService.getGlobalNotifications(user));
        model.addAttribute("username", user.getUserName());
        return "notification";
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
        model.addAttribute("user", user);
        return "settings";
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
    public String assets(@RequestBody String dataString, Model model){
        System.out.println(dataString);
        dataString = loginAndRegisterService.removeCharFromHtmlFormData(dataString);
        JSONObject dataJson = loginAndRegisterService.HtmlFromStringToJson(dataString);
        System.out.println(dataJson.toString());

        switch (dataJson.getString("assetType")) {
            case "stock":
                if(user.getAssets().getStockBySymbol(dataJson.getString("assetName")) == null){
                    String errorString = "Requested stock with symbol: "
                            + dataJson.getString("assetName") + " for user with id: " +
                            user.getUserId() + " not found!";
                    logger.error(errorString);
                    return "redirect:/user";
                }
                else{
                    model.addAttribute("username", user.getUserName());
                    Stock stock = user.getAssets().getStockBySymbol(dataJson.getString("assetName"));
                    model.addAttribute("stock", stock);
                    model.addAttribute("purchase", stock.getAllPurchases());
                    return "assets/stock";
                }
            case "passive-resource":
                if(user.getAssets().getPassiveResourceByName(dataJson.getString("assetName")) == null){
                    String errorString = "Requested passive resource with name: "
                            + dataJson.getString("assetName") + " for user with id: " +
                            user.getUserId() + " not found!";
                    logger.error(errorString);
                    return "redirect:/user";
                }
                else{
                    model.addAttribute("username", user.getUserName());
                    PassiveResource passiveResource = user.getAssets().getPassiveResourceByName(dataJson.getString("assetName"));
                    model.addAttribute("passive_resource", passiveResource);
                    return "assets/passive-resource";
                }
            case "index":
                if(user.getAssets().getIndexBySymbol(dataJson.getString("assetName")) == null){
                    String errorString = "Requested index with symbol: "
                            + dataJson.getString("assetName") + " for user with id: " +
                            user.getUserId() + " not found!";
                    logger.error(errorString);
                    return "redirect:/user";
                }
                else{
                    model.addAttribute("username", user.getUserName());
                    Index index = user.getAssets().getIndexBySymbol(dataJson.getString("assetName"));
                    model.addAttribute("index", index);
                    model.addAttribute("purchase", index.getAllPurchases());
                    return "assets/index";
                }
            case "crypto":
                if(user.getAssets().getCryptoBySymbol(dataJson.getString("assetName")) == null){
                    String errorString = "Requested crypto with symbol: "
                            + dataJson.getString("assetName") + " for user with id: " +
                            user.getUserId() + " not found!";
                    logger.error(errorString);
                    return "redirect:/user";
                }
                else{
                    model.addAttribute("username", user.getUserName());
                    Crypto crypto = user.getAssets().getCryptoBySymbol(dataJson.getString("assetName"));
                    model.addAttribute("crypto", crypto);
                    model.addAttribute("purchase", crypto.getAllPurchases());
                    return "assets/crypto";
                }
            case "commodity":
                if(user.getAssets().getCommodityByName(dataJson.getString("assetName")) == null){
                    String errorString = "Requested commodity with name: "
                            + dataJson.getString("assetName") + " for user with id: " +
                            user.getUserId() + " not found!";
                    logger.error(errorString);
                    return "redirect:/user";
                }
                else{
                    model.addAttribute("username", user.getUserName());
                    Commodities commodity = user.getAssets().getCommodityByName(dataJson.getString("assetName"));
                    model.addAttribute("commodity", commodity);
                    model.addAttribute("purchase", commodity.getAllPurchases());
                    return "assets/commodity";
                }
            default:
                    logger.error("Default in switch /asset post.");
                    return "redirect:/user";
        }
    }

    @RequestMapping(value = "/get-chart-data", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> getChartData(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        GraphService graphService = new GraphService();
        List<GraphInfo> graphInfoList = graphService.getChartData(jsonObject);
        return new ResponseEntity<>(graphInfoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-purchase", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Object> addPurchase(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        HashMap<String, String> map = baseService.addPurchaseToActiveAsset(jsonObject, user);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-asset", method = RequestMethod.GET)
    public String addAsset(Model model){
        model.addAttribute("username", user.getUserName());
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

    /*
    @RequestMapping(value = "/log-out", method = RequestMethod.GET)
    public String logOut(){
        this.user = null;
        return "redirect:/";
    }
     */


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
