package src.technical;

import src.business.User;
import java.sql.*;

/**
 * Database handler for User operations
 */
public class UserHandler {

    /**
     * Register a new user
     */
    public boolean registerUser(User user) {
        String insertSQL = "INSERT INTO users (user_name, user_email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getRole().toString());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticate user by email and password
     */
    public User authenticateUser(String email, String passwordHash) {
        String query = "SELECT user_id, user_name, user_email, password_hash, role FROM users WHERE user_email = ? AND password_hash = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, passwordHash);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get user by email
     */
    public User getUserByEmail(String email) {
        String query = "SELECT user_id, user_name, user_email, password_hash, role FROM users WHERE user_email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    try {
                        return new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_email"),
                            rs.getString("password_hash"),
                            rs.getString("role")
                        );
                    } catch (Exception e) {
                        System.err.println("Error creating User object: " + e.getMessage());
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getUserByEmail: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            // Don't throw, return null so AuthController can handle it
        } catch (Exception e) {
            System.err.println("Unexpected error in getUserByEmail: " + e.getMessage());
            e.printStackTrace();
            // Don't throw, return null so AuthController can handle it
        }
        return null;
    }

    /**
     * Get user by ID
     */
    public User getUserById(int userId) {
        String query = "SELECT user_id, user_name, user_email, password_hash, role FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check if email already exists
     */
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE user_email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

