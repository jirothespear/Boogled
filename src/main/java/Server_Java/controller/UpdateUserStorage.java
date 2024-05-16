package Server_Java.controller;

public class UpdateUserStorage {
    public static  int userId;
    public static String username;
    public static String password;




    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        UpdateUserStorage.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UpdateUserStorage.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UpdateUserStorage.password = password;
    }
}
