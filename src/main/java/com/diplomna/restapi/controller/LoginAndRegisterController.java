package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegisterService;
import com.diplomna.users.sub.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginAndRegisterController {

    @Autowired
    private LoginAndRegisterService loginAndRegisterService;

    public LoginAndRegisterController(){
        loginAndRegisterService = new LoginAndRegisterService();
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
        String result = loginAndRegisterService.verifyLogin(inputString);
        if(result.equals("Successful login!")){
            String[] temp = inputString.split("&");
            String inputUsername = temp[0].substring(9);
            User user = loginAndRegisterService.getUserByName(inputUsername);
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
        String result = loginAndRegisterService.createUser(inputString);
        if(result.equals("Passwords don't match") || result.equals("Email already taken")){
            attributes.addFlashAttribute("registerstatus", result);
            return new RedirectView("register");
        }
        else if(result.equals("Account created successfully!")){
            String[] temp = inputString.split("&");
            String inputUsername = temp[0].substring(9);
            User user = loginAndRegisterService.getUserByName(inputUsername);
            attributes.addFlashAttribute("user", user);
            return new RedirectView("user");
        }
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

}
