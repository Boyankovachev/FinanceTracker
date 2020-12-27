package com.diplomna.restapi.controller;

import com.diplomna.assets.finished.Index;
import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.sub.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private BaseService baseService;

    public BaseController(){
        baseService = new BaseService();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserHomepage(@ModelAttribute("user") User user, Model model){
        model.addAttribute("user", user);
        //baseService.getStocksByUserId(1);
        //baseService.getPassiveResourcesByUserId(2);
        //baseService.getIndexByUserId(1);
        //baseService.getCryptoByUserId(1);
        //baseService.getCommodityByUserId(1);
        return "user-homepage";
    }
}
