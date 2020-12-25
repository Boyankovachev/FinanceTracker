package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.BaseService;
import com.diplomna.users.sub.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        baseService.getStocksByUserId(1);
        return "user-homepage";
    }
}
