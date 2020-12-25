package com.diplomna.restapi.service;

import com.diplomna.database.insert.InsertIntoDb;
import com.diplomna.database.read.ReadFromDb;
import com.diplomna.users.UserManager;
import com.diplomna.users.sub.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LoginAndRegisterService {

    public LoginAndRegisterService(){}

    public String createUser(String inputString){
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
        return "Account created successfully!";
    }

    private boolean isEmailTaken(String email){
        ReadFromDb readFromDb = new ReadFromDb("test");
        UserManager userManager = readFromDb.readUsers();
        return userManager.isEmailPresent(email);
    }

    public String verifyLogin(String inputString){
        String[] temp = inputString.split("&");
        //System.out.println(inputString);
        String inputUsername = temp[0].substring(9);
        String inputPassword = temp[1].substring(9);
        //System.out.println(inputUsername);
        //System.out.println(inputPassword);
        ReadFromDb readFromDb = new ReadFromDb("test");
        UserManager userManager = readFromDb.readUsers();
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
        UserManager userManager = readFromDb.readUsers();
        return userManager.getUserByName(name);
    }
}
