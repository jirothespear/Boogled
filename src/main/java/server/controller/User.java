package server.controller;

/**
 * User object
 */
public class User {
    private String username;
    private String password;

    public User() {
        username = "null";
    }

    public User(String username){// used in queueing system
        this.username= username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
