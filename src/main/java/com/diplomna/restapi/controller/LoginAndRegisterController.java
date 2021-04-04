package com.diplomna.restapi.controller;

import com.diplomna.restapi.service.LoginAndRegisterService;
import com.diplomna.singleton.CurrentData;
import com.diplomna.users.sub.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Arrays;
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
        if(loginStatus!=null){
            model.addAttribute("message", loginStatus);
        }
        return "login";
    }

    @RequestMapping(value = "/confirm-login", method = RequestMethod.GET)
    public String confirmLogin(Principal principal){
        loginAndRegisterService.setAuthentication(principal.getName());
        return "confirm2fa";
    }
    @RequestMapping(value = "/confirm-login", method = RequestMethod.POST)
    public RedirectView confirmLogin(@RequestBody String input, RedirectAttributes attributes, Principal principal){

        if(loginAndRegisterService.checkAuthentication(input.split("=")[1])){
            Authentication newAuth = new UsernamePasswordAuthenticationToken(principal.getName(),
                    SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            return new RedirectView("user");
        }
        else {
            attributes.addFlashAttribute("loginstatus", "Wrong code! Try again.");
            loginAndRegisterService.setAuthentication(principal.getName());
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

        JSONObject jsonObject = loginAndRegisterService.HtmlFromStringToJson(inputString);
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

}
