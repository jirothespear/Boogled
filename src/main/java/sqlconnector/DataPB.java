package sqlconnector;

import server.controller.User;

import java.sql.*;

/**
 * DataPB class is a class that is use in creating queries
 */
public class DataPB {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3308/samtech?user=root&password";

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
        String query = "SELECT u.username AS username u FROM user WHERE u.usermame = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                score = rs.getInt("username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return score;
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
