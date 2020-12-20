package com.diplomna.users;

import com.diplomna.users.sub.User;

import java.util.List;

public class TestUser {
    static public void main(String []args){
        User user = new User();
        List<String> list = user.generateSaltAndHash("parola2");
        int i;
        for(i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }

    }
}
