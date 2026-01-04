package src.presentation;

import src.controllers.AuthController;
import src.business.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User Dashboard for viewing news and adding comments
 */
public class UserDashboard extends JFrame {

    public UserDashboard() {
        try {
            // Verify user is logged in
            if (!AuthController.isLoggedIn()) {
                JOptionPane.showMessageDialog(null, "Please login first!", "Error", JOptionPane.ERROR_MESSAGE);
                new LoginFrame();
                dispose();
                return;
            }

            initializeUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error initializing User Dashboard: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            new LoginFrame();
            dispose();
        }
    }

    private void initializeUI() {
        User currentUser = AuthController.getCurrentUser();
        
        // Safety check
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "User session expired. Please login again.", "Session Error", JOptionPane.ERROR_MESSAGE);
            AuthController.logout();
            dispose();
            new LoginFrame();
            return;
        }
        
        setTitle("NewsVine - User Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(46, 125, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("User Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(new Color(46, 125, 50));
        JLabel userLabel = new JLabel("Welcome, " + currentUser.getUserName());
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("🚪 Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setBackground(new Color(240, 255, 240));
        logoutButton.setForeground(new Color(46, 125, 50));
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 125, 50), 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            AuthController.logout();
            dispose();
            new LoginFrame();
        });
        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(10));
        userPanel.add(logoutButton);
        headerPanel.add(userPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonPanel.setBackground(new Color(245, 245, 250));

        JButton viewNewsButton = createMenuButton("View News", new Color(46, 125, 50));
        viewNewsButton.addActionListener(e -> {
            new UserNewsViewScreen(this);
            setVisible(false);
        });

        JButton searchNewsButton = createMenuButton("Search News", new Color(70, 130, 180));
        searchNewsButton.addActionListener(e -> {
            new SearchNewsScreen(this);
            setVisible(false);
        });

        buttonPanel.add(viewNewsButton);
        buttonPanel.add(searchNewsButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(new Color(245, 245, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h2>Welcome to NewsVine</h2>" +
                "<p>Your trusted source for authentic news.</p>" +
                "<p><b>Available Actions:</b></p>" +
                "<ul style='text-align: left;'>" +
                "<li>View all published news articles</li>" +
                "<li>Search news by title, content, or category</li>" +
                "<li>Add comments to news articles</li>" +
                "<li>Read comments from other users</li>" +
                "</ul>" +
                "</div></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(infoLabel, gbc);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(300, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}

