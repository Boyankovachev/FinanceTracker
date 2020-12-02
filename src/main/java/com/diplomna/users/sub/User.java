package com.diplomna.users.sub;

import com.diplomna.assets.AssetManager;

import java.util.List;

public class User {
    private String userName;
    private String password;
    private String email;
    private AssetManager assets;
    private boolean Is2FactorAuthenticationRequired;
    private List<TrackSet> trackSets;

}
