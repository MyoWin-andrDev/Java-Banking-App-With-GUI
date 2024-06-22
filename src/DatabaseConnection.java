package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection initializeDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankingDB?user=root&password=Phyo@105438");
                System.out.println("Successfully connected to BankingDB");
                return connect;
            } catch (SQLException e) {
                System.out.println("Error connecting to BankingDB" + e.getMessage());
                e.printStackTrace();
                return null;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the BankingDB driver" + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}

