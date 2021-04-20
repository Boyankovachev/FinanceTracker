package com.diplomna.restapi.service;

import com.diplomna.database.DatabaseConnection;
import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.email.EmailService;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.User;
import org.json.JSONException;
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
        try {
            if (!jsonObject.getString("password").equals(jsonObject.getString("password2"))) {
                return "Passwords don't match";
            }

            User user = new User();

            user.setUserName(jsonObject.getString("username"));

            HashMap<String, String> temp2 = user.generateSaltAndHash(jsonObject.getString("password"));
            user.setPassword(temp2.get("hash"));
            user.setSalt(temp2.get("salt"));

            String email = jsonObject.getString("email").replace("%40", "@");
            if (isEmailTaken(email)) {
                return "Email already taken";
            } else {
                user.setEmail(email);
            }

            dbConnection.add().insertUser(user);
            logger.info("Created account with email " + user.getEmail());
            return "Account created successfully!";

        }catch (JSONException jsonException){
            return "Missing input data!";
        }
    }

    private boolean isEmailTaken(String email){
        /*
            Check if email is present in database
         */
        UserManager userManager = dbConnection.read().readUsers();
        return userManager.isEmailPresent(email);
    }

    public User getUserByName(String name){
        //returns the first user found with matching name
        UserManager userManager = dbConnection.read().readUsers();
        return userManager.getUserByName(name);
    }

    public User getUserByEmail(String email){
        //returns the first user found with matching name
        // (for future) - fix when 2 identical names
        UserManager userManager = dbConnection.read().readUsers();
        return userManager.getUserByEmail(email);
    }

    public void setAuthentication(String email){
        /*
            generate 2fk random key
            send authentication to user
         */
        User user = dbConnection.read().readUsers().getUserByEmail(email);
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

    public JSONObject HtmlFromStringToJson(String htmlString){
        /*
        Converts HTML from submitted data to JSONObject
         */
        JSONObject jsonObject = new JSONObject();
        for(String string: htmlString.split("&")){
            String[] temp = string.split("=");
            if(temp.length>1) {
                jsonObject.put(temp[0], temp[1]);
            }
        }
        return jsonObject;
    }

    public String removeCharFromHtmlFormData(String string){
        string = string.replace("+", " ");
        return string;
    }

}

