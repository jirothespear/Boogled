package server.controller;

import Utility.ClientCallback;

/**
 * User object
 */
public class User implements Comparable<User> {
    private String username;
    private String password;

    private ClientCallback userCallback;

    public User() {
        username = "null";
    }

    public User(String username, ClientCallback callback){// used in queueing system

        this.username= username;
        this.userCallback = callback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ClientCallback getUserCallback() {
        return userCallback;
    }

    public void setUserCallback(ClientCallback userCallback) {
        this.userCallback = userCallback;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
