package sqlconnector;

import server.controller.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * DataPB class is a class that is use in creating queries
 */
public class DataPB {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/samtech?user=root&password";

    private DataPB() {
    }
    public static void setCon() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Database connected at url: " + url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static boolean checkUser(String usrnameWithPasswd){
        String user[]= usrnameWithPasswd.split("/");
        String query = "SELECT * FROM user WHERE user.username = ? AND user.password = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,user[0]);
            ps.setString(2,user[1]); //
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return  true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    public static User getUser(String usrname){
        String query = "SELECT u.username AS usrName , u.password AS pswd FROM user u WHERE u.username = ?;";
        User user = new User();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,usrname);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user.setUsername(rs.getString("usrName"));
                user.setPassword(rs.getString("pswd"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static boolean createUser(String username, String password) {
        String query = "INSERT INTO user (username, password, overallScore) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, 0);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkDuplicate(String username){
        String query = "SELECT u.username AS usrName , u.password AS pswd FROM user u WHERE u.username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * This method is created for getting the of the list of users to be displayed in the user-settings GUI
     * @return
     */
    public static ArrayList<User> getUsers(){
        String query = "SELECT username, password FROM user";
        ArrayList<User> listOfUsers = new ArrayList<>();
        User user = new User();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listOfUsers.add(user);
        return listOfUsers;
    }

    public static boolean updateUser(int userId, String newUsername, String newPassword) {
        String query = "UPDATE user SET username = ?, password = ? WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newUsername);
            ps.setString(2, newPassword);
            ps.setInt(3, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(int userId) {
        String query = "UPDATE user SET username = ?, password = ? WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setScore(String username, int newScore){
        String query = "UPDATE user SET overallScore = ?  WHERE username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,newScore );
            ps.setString(2,username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getScoreOfUser(String username){
        int score=0;
        String query = "SELECT overallScore FROM user WHERE username = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                score = rs.getInt("overallScore");
                rs.close();
                ps.close();
                return score;
            } else {
                rs.close();
                ps.close();
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static  boolean checkWord(String answer){
        String query = "SELECT * FROM words WHERE word = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,answer);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return  true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
