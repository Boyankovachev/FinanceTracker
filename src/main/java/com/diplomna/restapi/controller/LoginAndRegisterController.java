package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegisterService;
import com.diplomna.singleton.CurrentData;
import com.diplomna.users.sub.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;

@Controller
public class LoginAndRegisterController {

    @Autowired
    private final LoginAndRegisterService loginAndRegisterService;

    private User user;

    public LoginAndRegisterController(){
        loginAndRegisterService = new LoginAndRegisterService();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(@ModelAttribute("loginstatus") String loginStatus, Model model){
        if(loginStatus!=null){
            model.addAttribute("message", loginStatus);
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RedirectView getLogin(@RequestBody String inputString, RedirectAttributes attributes){

        JSONObject jsonObject = HtmlFromStringToJson(inputString);

        String result = loginAndRegisterService.verifyLogin(jsonObject);
        if(result.equals("Successful login!")){
            User user = loginAndRegisterService.getUserByName(jsonObject.getString("username"));
            attributes.addFlashAttribute("user", user);
            if(user.getIs2FactorAuthenticationRequired()){
                this.user = user;
                return new RedirectView("confirm-login");
            }
            return new RedirectView("user");
        }
        else {
            attributes.addFlashAttribute("loginstatus", "Invalid username or password");
            return new RedirectView("login");
        }
    }

    @RequestMapping(value = "/confirm-login", method = RequestMethod.GET)
    public String confirmLogin(){
        loginAndRegisterService.setAuthentication(this.user);
        return "confirm2fa";
    }
    @RequestMapping(value = "/confirm-login", method = RequestMethod.POST)
    public RedirectView confirmLogin(@RequestBody String input, RedirectAttributes attributes){

        if(loginAndRegisterService.checkAuthentication(input.split("=")[1])){
            attributes.addFlashAttribute("user", user);
            this.user = null;
            return new RedirectView("user");
        }
        else {
            this.user = null;
            attributes.addFlashAttribute("loginstatus", "Wrong code! Try again.");
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

        JSONObject jsonObject = HtmlFromStringToJson(inputString);
        String result = loginAndRegisterService.createUser(jsonObject);

        if(result.equals("Passwords don't match") || result.equals("Email already taken")){
            attributes.addFlashAttribute("registerstatus", result);
            return new RedirectView("register");
        }
        else if(result.equals("Account created successfully!")){
            User user = loginAndRegisterService.getUserByName(jsonObject.getString("username"));
            attributes.addFlashAttribute("user", user);
            return new RedirectView("user");
        }
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }



    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
        System.out.println("in test 1 get");
        //currentData.printAssetManager();
        return "test";
    }
    @RequestMapping(value="/test", method = RequestMethod.POST)
    public ModelAndView testPost(/*@RequestBody String string, */){
        System.out.println("in test 1 post");
        //attributes.addFlashAttribute("string", "neshto");
        return new ModelAndView("forward:/test2");
    }
    @RequestMapping(value="/test2", method = RequestMethod.GET)
    public String test2(/*@ModelAttribute("string") String string, Model model*/){
        System.out.println("in test 2 get");
        //model.addAttribute("string", string);
        return "test2";
    }

    private JSONObject HtmlFromStringToJson(String htmlString){
        JSONObject jsonObject = new JSONObject();
        for(String string: htmlString.split("&")){
            String[] temp = string.split("=");
            if(temp.length>1) {
                jsonObject.put(temp[0], temp[1]);
            }
        }
        return jsonObject;
    }
}
