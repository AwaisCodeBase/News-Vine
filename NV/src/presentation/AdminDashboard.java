package src.presentation;

import src.controllers.AuthController;
import src.controllers.NewsControllerView;
import src.business.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Admin Dashboard with full CRUD operations
 */
public class AdminDashboard extends JFrame {

    private NewsControllerView newsController;
    private JPanel mainPanel;

    public AdminDashboard() {
        try {
            // Verify admin access
            if (!AuthController.isAdmin()) {
                JOptionPane.showMessageDialog(null, "Access denied! Admin privileges required.", "Error", JOptionPane.ERROR_MESSAGE);
                new LoginFrame();
                dispose();
                return;
            }

            newsController = new NewsControllerView();
            initializeUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error initializing Admin Dashboard: " + e.getMessage(), 
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
        
        setTitle("NewsVine - Admin Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(new Color(70, 130, 180));
        JLabel userLabel = new JLabel("Welcome, " + currentUser.getUserName() + " (Admin)");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("🚪 Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setBackground(new Color(240, 248, 255));
        logoutButton.setForeground(new Color(70, 130, 180));
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
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
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 250));

        JButton postButton = createMenuButton("📰 Post News", new Color(70, 130, 180));
        postButton.addActionListener(e -> {
            new PostingNewsScreen(this);
            setVisible(false);
        });

        JButton viewButton = createMenuButton("👁️ View News", new Color(46, 125, 50));
        viewButton.addActionListener(e -> {
            new DisplayNewsScreen(this);
            setVisible(false);
        });

        JButton editButton = createMenuButton("✏️ Edit News", new Color(255, 152, 0));
        editButton.addActionListener(e -> {
            // Open DisplayNewsScreen first to select which news to edit
            new DisplayNewsScreen(this);
            setVisible(false);
        });

        JButton deleteButton = createMenuButton("🗑️ Delete News", new Color(198, 40, 40));
        deleteButton.addActionListener(e -> {
            new DeleteNewsScreen(this);
            setVisible(false);
        });

        buttonPanel.add(postButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(new Color(245, 245, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h2>Admin Control Panel</h2>" +
                "<p>You have full access to manage all news articles.</p>" +
                "<p><b>Available Actions:</b></p>" +
                "<ul style='text-align: left;'>" +
                "<li>Post new news articles</li>" +
                "<li>View all published news</li>" +
                "<li>Edit existing news articles</li>" +
                "<li>Delete news articles</li>" +
                "<li>Manage comments</li>" +
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
        button.setForeground(new Color(255, 255, 255)); // Pure white for maximum contrast
        button.setPreferredSize(new Dimension(200, 90));
        button.setFocusPainted(false);
        
        // Calculate border color with proper clamping to avoid negative values
        int borderRed = Math.max(0, color.getRed() - 20);
        int borderGreen = Math.max(0, color.getGreen() - 20);
        int borderBlue = Math.max(0, color.getBlue() - 20);
        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(borderRed, borderGreen, borderBlue), 3),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect with proper clamping
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(
                    Math.min(255, Math.max(0, color.getRed() + 20)),
                    Math.min(255, Math.max(0, color.getGreen() + 20)),
                    Math.min(255, Math.max(0, color.getBlue() + 20))
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
}

