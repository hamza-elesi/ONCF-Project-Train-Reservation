package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/trainreservationsystem";
    private static final String USER = "root";
    private static final String PASSWORD ="0000";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}