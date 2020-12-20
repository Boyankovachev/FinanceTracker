package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegister;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private LoginAndRegister loginAndRegister;

    public WebController(){
        loginAndRegister = new LoginAndRegister();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void getLogin(@RequestBody String inputString){
        System.out.println(loginAndRegister.verifyLogin(inputString));
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void getRegister(@RequestBody String inputString){
        System.out.println(loginAndRegister.createUser(inputString));
    }


    @RequestMapping("/in")
    @ResponseBody
    public String pedal(){
        return "you are succesfully logged in";
    }

}
