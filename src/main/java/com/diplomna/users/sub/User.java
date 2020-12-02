package com.diplomna.users.sub;

import com.diplomna.assets.AssetManager;
import com.diplomna.assets.sub.Asset;

import java.util.List;

public class User {
    private String userName;
    private String password;
    private String email;
    private AssetManager assets;
    private boolean is2FactorAuthenticationRequired;
    private List<TrackSet> trackSets;

    public User(){}
    public User(String userName, String password, String email, AssetManager assets,
                boolean is2FactorAuthenticationRequired, List<TrackSet> trackSets){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.assets = assets;
        this.is2FactorAuthenticationRequired = is2FactorAuthenticationRequired;
        this.trackSets = trackSets;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
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

    public List<TrackSet> getTrackSets() {
        return trackSets;
    }
}
