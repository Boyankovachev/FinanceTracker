package com.diplomna.users.sub;

import com.diplomna.assets.AssetManager;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class User {
    private String userName;
    private String passwordHash;
    private String salt;
    private String email;
    private AssetManager assets = new AssetManager();
    private boolean is2FactorAuthenticationRequired;
    private List<Notification> notifications;
    private int userId;

    public User(){}
    public User(String userName, String passwordHash,String salt){
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }
    public User(int userId, String userName, String passwordHash,String salt, String email, Boolean is2fa){
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.salt = salt;
        if(email != null){
            this.email = email;
        }
        if(is2fa != null){
            this.is2FactorAuthenticationRequired = is2fa;
        }
    }
    public User(int userId, String userName, String passwordHash,String salt ,String email, AssetManager assets,
                boolean is2FactorAuthenticationRequired, List<Notification> notifications){
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.email = email;
        this.assets = assets;
        this.is2FactorAuthenticationRequired = is2FactorAuthenticationRequired;
        this.notifications = notifications;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public AssetManager getAssets() {
        return assets;
    }

    public boolean getIs2FactorAuthenticationRequired() {
        return is2FactorAuthenticationRequired;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAssets(AssetManager assets) {
        this.assets = assets;
    }

    public void setIs2FactorAuthenticationRequired(boolean is2FactorAuthenticationRequired) {
        this.is2FactorAuthenticationRequired = is2FactorAuthenticationRequired;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

    public String getSalt(){
        return salt;
    }

    public int getUserId(){
        return userId;
    }
    public void setUserId(int id){
        this.userId = id;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<String> generateSaltAndHash(String password){
        //Given a password generates a random salt and hashes the password
        //returns list[0] = hashed + salted password
        //        list[1] = salt

        //generate random salt
        Random r = new SecureRandom();
        byte[] salt = new byte[20];
        r.nextBytes(salt);
        String saltString = salt.toString();

        //generate hash
        String hash = Hashing.sha256()
                .hashString(password + saltString, StandardCharsets.UTF_8)
                .toString();

        List<String> result  = new ArrayList<>();
        result.add(hash);
        result.add(saltString);
        return result;
    }

    public boolean checkPassword(String inputPassword){
        //hash input password with user salt
        //and check against the existing hash
        String newHash = Hashing.sha256()
                .hashString(inputPassword + salt, StandardCharsets.UTF_8)
                .toString();
        return newHash.equals(passwordHash);
    }

}
