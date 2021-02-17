package com.diplomna.restapi.service;

import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.email.EmailService;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class LoginAndRegisterService {

    @Autowired
    private EmailService emailService;

    private String authenticateCode;
    private User user;

    private final Logger logger;
    public LoginAndRegisterService(){
        this.logger = LoggerFactory.getLogger(BaseService.class);
    }

    public String createUser(String inputString){
        /*
            Create user and add to database
         */
        String[] temp = inputString.split("&");
        String inputUsername = temp[0].substring(9);
        String inputPassword = temp[1].substring(9);
        String inputPassword2 = temp[2].substring(10);
        String inputEmail = temp[3].substring(6);

        if(!inputPassword.equals(inputPassword2)){
            //pokazvam na web stranicata che parolite ne suvpadat
            return "Passwords don't match";
        }

        User user = new User();
        user.setUserName(inputUsername);
        List<String> temp2 = user.generateSaltAndHash(inputPassword);
        user.setPassword(temp2.get(0));
        user.setSalt(temp2.get(1));

        if(!inputEmail.equals("")){
            inputEmail = inputEmail.replace("%40", "@");
            if(isEmailTaken(inputEmail)){
                //tuka pokazvam na web stranicata che meila veche e registriran
                return "Email already taken";
            }
            else {
                user.setEmail(inputEmail);
            }
        }
        InsertIntoDb insert = new InsertIntoDb("test");
        insert.insertUser(user);
        logger.info("Created account with username " + user.getUserName());
        return "Account created successfully!";
    }

    private boolean isEmailTaken(String email){
        /*
            Check if email is present in database
         */
        ReadFromDb readFromDb = new ReadFromDb("test");
        UserManager userManager = readFromDb.readUsers(false);
        return userManager.isEmailPresent(email);
    }

    public String verifyLogin(String inputString){
        /*
            Check password and username
            return response
         */
        String[] temp = inputString.split("&");
        String inputUsername = temp[0].substring(9);
        String inputPassword = temp[1].substring(9);
        ReadFromDb readFromDb = new ReadFromDb("test");
        UserManager userManager = readFromDb.readUsers(false);
        if(!userManager.isUsernamePresent(inputUsername)){
            return "Incorrect username or password!";
        }
        User user = userManager.getUserByName(inputUsername);

        if(!user.checkPassword(inputPassword)){
            return "Incorrect username or password!";
        }
        return "Successful login!";
    }

    public User getUserByName(String name){
        //retuns the first user found with matching name
        // (for future) - fix when 2 identical names
        ReadFromDb readFromDb = new ReadFromDb("test");
        UserManager userManager = readFromDb.readUsers(false);
        return userManager.getUserByName(name);
    }

    public void setAuthentication(User user){
        /*
            generate 2fk key
            send authentication to user
         */
        this.authenticateCode = generateKey();
        this.user = user;
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
    public User getUser() {
        User temp = this.user;
        this.user = null;
        return temp;
    }
}
