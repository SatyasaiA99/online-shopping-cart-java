package com.shashi.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {

    private static Connection conn;

    public static Connection provideConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                System.out.println("🔍 Loading DB properties...");

                // Load application.properties
                ResourceBundle rb = ResourceBundle.getBundle("application");

                String connectionString = rb.getString("db.connectionString");
                String driverName = rb.getString("db.driverName");
                String username = rb.getString("db.username");
                String password = rb.getString("db.password");

                System.out.println("DB URL: " + connectionString);
                System.out.println("DB USER: " + username);

                // Load Driver
                Class.forName(driverName);

                // Create Connection
                conn = DriverManager.getConnection(connectionString, username, password);

                System.out.println("✅ Database Connected Successfully!");

            }
        } catch (Exception e) {
            System.out.println("❌ DB Connection Failed!");
            e.printStackTrace();
        }

        return conn;
    }

    // Close ResultSet
    public static void closeConnection(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close PreparedStatement
    public static void closeConnection(PreparedStatement ps) {
        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
