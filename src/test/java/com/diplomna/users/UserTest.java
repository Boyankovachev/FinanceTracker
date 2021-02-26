package com.diplomna.users;

import com.diplomna.assets.AssetManager;
import com.diplomna.users.sub.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        User user = new User(154,"Ivan","1234","sol","randomemail@gmail.com",new AssetManager(),
                false, new ArrayList<>());

        assertEquals(user.getUserId(), 154);
        assertEquals(user.getUserName(), "Ivan");
        assertEquals(user.getPasswordHash(), "1234");
        assertEquals(user.getSalt(), "sol");
        assertEquals(user.getEmail(), "randomemail@gmail.com");
        assertEquals(user.getIs2FactorAuthenticationRequired(), false);
        assertEquals(user.getNotifications().size(), 0);
    }

    @Test
    public void testPasswords(){
        User user = new User();
        HashMap<String, String> data = user.generateSaltAndHash("parolata1234");
        user.setPassword(data.get("hash"));
        user.setSalt(data.get("salt"));
        assertTrue(user.checkPassword("parolata1234"));
    }

}
