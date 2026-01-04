package src.business;

/**
 * User entity class with role-based access control
 */
public class User {

    public enum Role {
        ADMIN, USER
    }

    private int user_id;
    private String user_name;
    private String user_email;
    private String password_hash;
    private Role role;

    // Constructor for creating new user
    public User(String user_name, String user_email, String password_hash, Role role) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.password_hash = password_hash;
        this.role = role;
    }

    // Constructor for loading from database
    public User(int user_id, String user_name, String user_email, String password_hash, Role role) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password_hash = password_hash;
        this.role = role;
    }

    // Constructor for loading from database with string role
    public User(int user_id, String user_name, String user_email, String password_hash, String role) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password_hash = password_hash;
        this.role = Role.valueOf(role.toUpperCase());
    }

    // Getters
    public int getUserId() {
        return user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public String getUserEmail() {
        return user_email;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isUser() {
        return role == Role.USER;
    }

    // Setters
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }
}

