package src.presentation;

import src.controllers.AuthController;
import src.business.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login and Registration Frame with role-based access
 */
public class LoginFrame extends JFrame {
    
    private JTextField emailField;
    private JPasswordField loginPasswordField;
    private JPasswordField registerPasswordField;
    private JTextField nameField;
    private JTextField registerEmailField;
    private JComboBox<String> roleComboBox;
    private AuthController authController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public LoginFrame() {
        authController = new AuthController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("NewsVine - Login");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Use CardLayout for Login/Register switching
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Login Panel
        cardPanel.add(createLoginPanel(), "LOGIN");
        // Register Panel
        cardPanel.add(createRegisterPanel(), "REGISTER");

        add(cardPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("NewsVine");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 20, 20, 20);
        panel.add(titleLabel, gbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Authentic News Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 30, 20);
        panel.add(subtitleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailLabel, gbc);

        // Email Field
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordLabel, gbc);

        // Password Field (for login panel)
        loginPasswordField = new JPasswordField(20);
        loginPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginPasswordField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginPasswordField, gbc);

        // Login Button
        JButton loginButton = new JButton("🔐 Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        loginButton.setPreferredSize(new Dimension(250, 45));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 10, 20);
        panel.add(loginButton, gbc);

        // Register Link
        JPanel registerPanel = new JPanel(new FlowLayout());
        registerPanel.setBackground(new Color(245, 245, 250));
        JLabel registerLabel = new JLabel("Don't have an account? ");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JButton registerLink = new JButton("Register");
        registerLink.setFont(new Font("Arial", Font.BOLD, 14));
        registerLink.setBackground(new Color(240, 248, 255));
        registerLink.setForeground(new Color(70, 130, 180));
        registerLink.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        registerLink.setFocusPainted(false);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "REGISTER");
            }
        });
        registerPanel.add(registerLabel);
        registerPanel.add(registerLink);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 20, 20, 20);
        panel.add(registerPanel, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Name Label
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nameLabel, gbc);

        // Name Field
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailLabel, gbc);

        // Email Field (for register panel)
        registerEmailField = new JTextField(20);
        registerEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
        registerEmailField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(registerEmailField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordLabel, gbc);

        // Password Field (for register panel)
        registerPasswordField = new JPasswordField(20);
        registerPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPasswordField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(registerPasswordField, gbc);

        // Role Label
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(roleLabel, gbc);

        // Role ComboBox
        roleComboBox = new JComboBox<>(new String[]{"USER", "ADMIN"});
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        roleComboBox.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(roleComboBox, gbc);

        // Register Button
        JButton registerButton = new JButton("📝 Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        registerButton.setPreferredSize(new Dimension(250, 45));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 10, 20);
        panel.add(registerButton, gbc);

        // Login Link
        JPanel loginPanel = new JPanel(new FlowLayout());
        loginPanel.setBackground(new Color(245, 245, 250));
        JLabel loginLabel = new JLabel("Already have an account? ");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JButton loginLink = new JButton("Login");
        loginLink.setFont(new Font("Arial", Font.BOLD, 14));
        loginLink.setBackground(new Color(240, 248, 255));
        loginLink.setForeground(new Color(70, 130, 180));
        loginLink.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        loginLink.setFocusPainted(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "LOGIN");
            }
        });
        loginPanel.add(loginLabel);
        loginPanel.add(loginLink);
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 20, 20, 20);
        panel.add(loginPanel, gbc);

        return panel;
    }

    private void handleLogin() {
        try {
            String email = emailField.getText().trim();
            String password = new String(loginPasswordField.getPassword()).trim();

            // Validate inputs before attempting login
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your email!", "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your password!", "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            User user = authController.loginUser(email, password);
            
            if (user != null) {
                // Successfully logged in
                dispose();
                
                // Open appropriate dashboard based on role
                try {
                    if (user.isAdmin()) {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                new AdminDashboard();
                            } catch (Exception e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, 
                                    "Error opening admin dashboard: " + e.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
                                new LoginFrame(); // Return to login on error
                            }
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                new UserDashboard();
                            } catch (Exception e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, 
                                    "Error opening user dashboard: " + e.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
                                new LoginFrame(); // Return to login on error
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error opening dashboard: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    // Don't dispose if dashboard fails to open
                    setVisible(true);
                }
            }
            // If user is null, loginUser already showed error message
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "An unexpected error occurred during login: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = registerEmailField.getText().trim();
        String password = new String(registerPasswordField.getPassword()).trim();
        String selectedRole = (String) roleComboBox.getSelectedItem();
        
        User.Role role = User.Role.valueOf(selectedRole);
        
        boolean success = authController.registerUser(name, email, password, role);
        
        if (success) {
            // Switch to login panel after successful registration
            cardLayout.show(cardPanel, "LOGIN");
            emailField.setText(email); // Pre-fill email
            loginPasswordField.setText(""); // Clear password
            registerPasswordField.setText(""); // Clear register password too
        }
    }
}

