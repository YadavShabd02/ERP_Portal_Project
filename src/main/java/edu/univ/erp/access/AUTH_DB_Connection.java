package edu.univ.erp.auth;

import java.sql.*;
// Changed import to match your structure

public class AUTH_DB_Connection {

    public int createUser(String username, String role, String passwordHash) {
        String sql = "INSERT INTO users_auth (username, role, password_hash, status, last_login) VALUES (?, ?, ?, ?, ?)";

        int generatedId = -1;


        try (Connection conn = AUTH_DB_Connection.getConnection(); // Changed class name
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            pstmt.setString(1, username);
            pstmt.setString(2, role);
            pstmt.setString(3, passwordHash);
            pstmt.setString(4, "Active");
            pstmt.setTimestamp(5, currentTimestamp);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        System.out.println("New user '" + username + "' created with ID: " + generatedId);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error during user creation for " + username);
            e.printStackTrace();
        }
        return generatedId;
    }
}