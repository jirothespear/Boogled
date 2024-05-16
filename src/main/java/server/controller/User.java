package server.controller;

import Utility.ClientCallback;

/**
 * User object
 */
public class User implements Comparable<User> {
    private int userId;
    private String username;
    private String password;

    private ClientCallback userCallback;

    public User() {
        username = "null";
    }

    public User(int userId, String username, String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User(String username, ClientCallback callback){// used in queueing system

        this.username= username;
        this.userCallback = callback;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserCallback(ClientCallback userCallback) {
        this.userCallback = userCallback;
    }

    public int getUserId(){
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ClientCallback getUserCallback() {
        return userCallback;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
