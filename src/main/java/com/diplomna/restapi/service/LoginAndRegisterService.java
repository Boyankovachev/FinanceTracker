package com.diplomna.restapi.service;

import com.diplomna.database.DatabaseConnection;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.email.EmailService;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class LoginAndRegisterService {

    @Autowired
    private EmailService emailService;

    private String authenticateCode;
    private final DatabaseConnection dbConnection;
    private final Logger logger;

    public LoginAndRegisterService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
        dbConnection = new DatabaseConnection();
    }

    public String createUser(JSONObject jsonObject){
        /*
            Create user and add to database
         */
        if(!jsonObject.getString("password").equals(jsonObject.getString("password2"))){
            return "Passwords don't match";
        }

        User user = new User();
        user.setUserName(jsonObject.getString("username"));
        HashMap<String , String> temp2 = user.generateSaltAndHash(jsonObject.getString("password"));
        user.setPassword(temp2.get("hash"));
        user.setSalt(temp2.get("salt"));

        if(!jsonObject.getString("email").equals("")){
            String email = jsonObject.getString("email").replace("%40", "@");
            if(isEmailTaken(email)){
                return "Email already taken";
            }
            else {
                user.setEmail(email);
            }
        }
        dbConnection.add().insertUser(user);
        logger.info("Created account with username " + user.getUserName());
        return "Account created successfully!";
    }

    private boolean isEmailTaken(String email){
        /*
            Check if email is present in database
         */
        UserManager userManager = dbConnection.read().readUsers();
        return userManager.isEmailPresent(email);
    }

    public String verifyLogin(JSONObject jsonObject){
        /*
            Check password and username
            return response
         */
        UserManager userManager = dbConnection.read().readUsers();
        if(!userManager.isUsernamePresent(jsonObject.getString("username"))){
            return "Incorrect username or password!";
        }
        User user = userManager.getUserByName(jsonObject.getString("username"));

        if(!user.checkPassword(jsonObject.getString("password"))){
            return "Incorrect username or password!";
        }
        return "Successful login!";
    }

    public User getUserByName(String name){
        //retuns the first user found with matching name
        // (for future) - fix when 2 identical names
        UserManager userManager = dbConnection.read().readUsers();
        return userManager.getUserByName(name);
    }

    public void setAuthentication(User user){
        /*
            generate 2fk random key
            send authentication to user
         */
        this.authenticateCode = generateKey();
        emailService.sendAuthenticationKeyEmail(authenticateCode, user.getEmail());
    }

    private String generateKey(){
        //generate 6 digit random string
        return String.format("%06d", new Random().nextInt(999999));
    }

    public boolean checkAuthentication(String input){
        //check 2fa user input
        if(authenticateCode.equals(input)){
            authenticateCode = null;
            return true;
        }
        else {
            authenticateCode = null;
            return false;
        }
    }
}
