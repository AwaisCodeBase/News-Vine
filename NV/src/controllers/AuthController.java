package src.controllers;

import src.business.User;
import src.technical.UserHandler;
import src.technical.PasswordUtil;
import javax.swing.JOptionPane;

/**
 * Authentication Controller with role-based access control
 */
public class AuthController {
    
    private UserHandler userHandler;
    private static User currentUser; // Session management

    public AuthController() {
        this.userHandler = new UserHandler();
    }

    /**
     * Register a new user
     */
    public boolean registerUser(String userName, String email, String password, User.Role role) {
        // Validate input
        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            JOptionPane.showMessageDialog(null, "Invalid email address!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (password == null || password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if email already exists
        if (userHandler.emailExists(email)) {
            JOptionPane.showMessageDialog(null, "Email already registered!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Hash password
        String passwordHash = PasswordUtil.hashPassword(password);

        // Create user
        User newUser = new User(userName, email, passwordHash, role);

        // Save to database
        boolean success = userHandler.registerUser(newUser);
        
        if (success) {
            JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }

    /**
     * Login user
     */
    public User loginUser(String email, String password) {
        try {
            // Validate and trim input
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your email!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            if (password == null || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your password!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Trim inputs
            email = email.trim();
            password = password.trim();

            // Get user from database
            User user = null;
            try {
                user = userHandler.getUserByEmail(email);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Database connection error. Please check your database connection.\nError: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Verify password - hash the input password and compare with stored hash
            if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                JOptionPane.showMessageDialog(null, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Set current user (session)
            currentUser = user;
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "An unexpected error occurred: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Get current logged-in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logout current user
     */
    public static void logout() {
        currentUser = null;
    }

    /**
     * Check if user is admin
     */
    public static boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * Check if user is logged in
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}

