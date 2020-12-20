package com.diplomna.users;

import com.diplomna.users.sub.User;

import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager(){}
    public UserManager(List<User> users){
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }
    public void addUsers(List<User> newUsers){
        users.addAll(newUsers);
    }

    public List<User> getUsers(){
        return users;
    }

    public User getUserByName(String userName){
        //ako ima poveche ot edin user sus edno i sushto ime
        //staa tupo, opravi go
        int i;
        for(i=0; i<users.size(); i++){
            if(users.get(i).getUserName().equals(userName)){
                return users.get(i);
            }
        }
        return null;
    }

    public int getUserIndexByName(String userName){
        //ako ima poveche ot edin user sus edno i sushto ime
        //staa tupo, opravi go
        int i;
        for(i=0; i<users.size(); i++){
            if(users.get(i).getUserName().equals(userName)){
                return i;
            }
        }
        return 0;
    }

    public User getUserByIndex(int index){
        try {
            return users.get(index);
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailPresent(String email){
        for(User user: users){
            if(user.getEmail()!=null){
                if(user.getEmail().equals(email)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUsernamePresent(String username){
        for(User user: users){
            if(user.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }
}
