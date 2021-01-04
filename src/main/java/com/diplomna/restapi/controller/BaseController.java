package com.diplomna.restapi.controller;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.finished.Index;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.sub.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
        model.addAttribute("assetName", assetName);
        model.addAttribute("assetType", assetType);
        return "assets/test";
    }

}
