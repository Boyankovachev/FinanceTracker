package com.diplomna.users;

import com.diplomna.assets.AssetManager;
import com.diplomna.users.sub.TrackSet;
import com.diplomna.users.sub.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUser(){

        String string1 = "purvi";
        String string2= "vtori";
        String string3 = "treti";
        List<String> strings = new ArrayList<>();
        strings.add(string1);
        strings.add(string2);
        strings.add(string3);
        TrackSet trackSet = new TrackSet(strings, 5.65);
        List<TrackSet> trackSetList = new ArrayList<>();
        trackSetList.add(trackSet);
        User user = new User("Ivan","1234","sol","randomemail@gmail.com",new AssetManager(),
                false, trackSetList);

        assertEquals(user.getUserName(), "Ivan");
        assertEquals(user.getPasswordHash(), "1234");
        assertEquals(user.getSalt(), "sol");
        assertEquals(user.getEmail(), "randomemail@gmail.com");
        assertEquals(user.getIs2FactorAuthenticationRequired(), false);
        assertEquals(user.getTrackSets().size(), 1);
        assertEquals(user.getTrackSets().get(0).getNames().size(), 3);
        assertEquals(user.getTrackSets().get(0).getNames().get(1), "vtori");
    }

}
