package com.example.sms.util;

import java.sql.*;

public class JdbcDemo {

    public static void printDbVersion(String url, String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Connected to: " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion());
        } catch (SQLException e) {
            System.err.println("JDBC connection failed: " + e.getMessage());
        }
    }
}
