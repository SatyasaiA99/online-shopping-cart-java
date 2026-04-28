package com.shashi.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {

    private static Connection conn;

    // ✅ Get Connection (FIXED VERSION)
    public static Connection provideConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                System.out.println("🔍 Loading DB properties...");

                ResourceBundle rb = ResourceBundle.getBundle("application");

                String url = rb.getString("db.connectionString");
                String driver = rb.getString("db.driverName");
                String user = rb.getString("db.username");
                String pass = rb.getString("db.password");

                System.out.println("DB URL: " + url);
                System.out.println("DB USER: " + user);

                // Load Driver
                Class.forName(driver);

                // Create Connection
                conn = DriverManager.getConnection(url, user, pass);

                System.out.println("✅ DB Connected Successfully!");
            }

        } catch (Exception e) {

            System.out.println("❌ DB CONNECTION FAILED (CHECK CONFIG)");
            e.printStackTrace();

            // 🔥 IMPORTANT: STOP HERE IF DB FAILS
            throw new RuntimeException("Database connection failed", e);
        }

        return conn;
    }

    // ✅ Close ResultSet
    public static void closeConnection(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Close PreparedStatement
    public static void closeConnection(PreparedStatement ps) {
        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Close Connection
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
