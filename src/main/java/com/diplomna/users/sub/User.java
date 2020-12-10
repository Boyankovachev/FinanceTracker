package com.diplomna.users.sub;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.sub.Asset;

import java.util.List;

public class User {
    private String userName;
    private String passwordHash;
    private String salt;
    private String email;
    private AssetManager assets;
    private boolean is2FactorAuthenticationRequired;
    private List<TrackSet> trackSets;
    private int userId;

    public User(){}
    public User(String userName, String passwordHash,String salt){
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }
    public User(int userId, String userName, String passwordHash,String salt ,String email, AssetManager assets,
                boolean is2FactorAuthenticationRequired, List<TrackSet> trackSets){
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.email = email;
        this.assets = assets;
        this.is2FactorAuthenticationRequired = is2FactorAuthenticationRequired;
        this.trackSets = trackSets;
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

    public void setTrackSets(List<TrackSet> trackSets) {
        this.trackSets = trackSets;
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


    public List<TrackSet> getTrackSets() {
        return trackSets;
    }
}
