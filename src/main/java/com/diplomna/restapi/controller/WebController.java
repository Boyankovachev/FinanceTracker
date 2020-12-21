package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegister;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView getLogin(@RequestBody String inputString, /*Model model */ RedirectAttributes attributes){
        String result = loginAndRegister.verifyLogin(inputString);
        /*
        if(result.equals("Successful login!")){
            return "redirect:/test";
        }
        else return "redirect:/test2";

         */
        /*
        if(result.equals("Successful login!")){
            model.addAttribute("message", "pone uceli parolata");
        }
        else model.addAttribute("message", "ne uceli parolata");;
        return "redirect:/logintest";

         */
        if(result.equals("Successful login!")){
            attributes.addAttribute("message", "login successful");
        }
        else attributes.addAttribute("message", "login failed");
        return new RedirectView("logintest");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void getRegister(@RequestBody String inputString){
        System.out.println(loginAndRegister.createUser(inputString));
    }

    @RequestMapping(value = "/logintest", method = RequestMethod.GET)
    public String getTest(Model model, @RequestParam String message){
        model.addAttribute("message", message);
        return "logintest";
    }

}
