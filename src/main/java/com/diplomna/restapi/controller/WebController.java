package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegister;
import com.diplomna.users.sub.User;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public String getLogin(@ModelAttribute("loginstatus") String loginStatus, Model model){
        if(loginStatus.equals("login failed")){
            model.addAttribute("message", "Invalid username or password");
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RedirectView getLogin(@RequestBody String inputString, RedirectAttributes attributes){
        String result = loginAndRegister.verifyLogin(inputString);
        if(result.equals("Successful login!")){
            String[] temp = inputString.split("&");
            String inputUsername = temp[0].substring(9);
            User user = loginAndRegister.getUserByName(inputUsername);
            attributes.addFlashAttribute("user", user);
            return new RedirectView("user");
        }
        else {
            attributes.addFlashAttribute("loginstatus", "login failed");
            return new RedirectView("login");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(@ModelAttribute("registerstatus") String registerStatus, Model model){
        if(registerStatus.equals("Passwords don't match") || registerStatus.equals("Email already taken")){
            model.addAttribute("message", registerStatus);
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView getRegister(@RequestBody String inputString, RedirectAttributes attributes){
        String result = loginAndRegister.createUser(inputString);
        if(result.equals("Passwords don't match") || result.equals("Email already taken")){
            attributes.addFlashAttribute("registerstatus", result);
            return new RedirectView("register");
        }
        else if(result.equals("Account created successfully!")){
            String[] temp = inputString.split("&");
            String inputUsername = temp[0].substring(9);
            User user = loginAndRegister.getUserByName(inputUsername);
            attributes.addFlashAttribute("user", user);
            return new RedirectView("user");
        }
        return null;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(@ModelAttribute("user") User user, Model model){
        model.addAttribute("user", user);
        return "user";
    }

}
