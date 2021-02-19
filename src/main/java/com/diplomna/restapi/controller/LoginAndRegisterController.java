package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegisterService;
import com.diplomna.singleton.CurrentData;
import com.diplomna.users.sub.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;

@Controller
public class LoginAndRegisterController {

    @Autowired
    private final LoginAndRegisterService loginAndRegisterService;

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
            if(user.getIs2FactorAuthenticationRequired()){
                return new RedirectView("confirm-login");
            }
            return new RedirectView("user");
        }
        else {
            attributes.addFlashAttribute("loginstatus", "login failed");
            return new RedirectView("login");
        }
    }

    @RequestMapping(value = "/confirm-login", method = RequestMethod.GET)
    public String confirmLogin(@ModelAttribute("user") User user){
        if(user.getUserName() == null || user.getEmail() == null){
            return "redirect:/";
        }
        loginAndRegisterService.setAuthentication(user);
        return "confirm2fa";
    }
    @RequestMapping(value = "/confirm-login", method = RequestMethod.POST)
    public ResponseEntity<Object> confirmLogin(@RequestBody String jsonString, RedirectAttributes attributes){
        JSONObject jsonObject = new JSONObject(jsonString);
        if(loginAndRegisterService.checkAuthentication(jsonObject.getString("code"))){
            HashMap<String, Object> responseMap = new HashMap<>();
            responseMap.put("response", "success");
            return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
        }
        else {
            HashMap<String, String> responseMap = new HashMap<>();
            responseMap.put("response", "Wrong code, you will be redirected to reLogin in a few seconds!");
            return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/redirect-to-user", method = RequestMethod.GET)
    public RedirectView redirectToUser(RedirectAttributes attributes){
        attributes.addFlashAttribute("user", loginAndRegisterService.getUser());
        return new RedirectView("user");
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



    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
        System.out.println("in test 1 get");
        CurrentData currentData = CurrentData.getInstance();
        //currentData.printAssetManager();
        return "test";
    }
    @RequestMapping(value="/test", method = RequestMethod.POST)
    public RedirectView test(/*@RequestBody String string, */RedirectAttributes attributes){
        System.out.println("in test 1 post");
        //attributes.addFlashAttribute("string", "neshto");
        return new RedirectView("test2");
    }
    @RequestMapping(value="/test2", method = RequestMethod.GET)
    public String test2(/*@ModelAttribute("string") String string, Model model*/){
        System.out.println("in test 2 get");
        //model.addAttribute("string", string);
        return "test2";
    }

}
