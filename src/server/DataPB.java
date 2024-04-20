package src.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DataPB class is a class that is use in creating queries
 */
public class DataPB {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/mogwarts?user=root&password";

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
        String query = "Select";

        return false;
    }
}
