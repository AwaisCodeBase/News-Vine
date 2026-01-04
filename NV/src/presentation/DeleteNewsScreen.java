
package src.presentation;

import javax.swing.*;
import src.controllers.NewsControllerView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Delete News Screen with proper UI
 */
public class DeleteNewsScreen extends JFrame {

    private JTextField postIdField;
    private JButton deleteButton, backButton;
    private JFrame previousScreen;

    public DeleteNewsScreen() {
        this(null);
    }

    public DeleteNewsScreen(JFrame previousScreen) {
        this.previousScreen = previousScreen;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Delete News - Admin");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel postIdLabel = new JLabel("News ID to Delete:");
        postIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        postIdLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(postIdLabel, gbc);

        postIdField = new JTextField(25);
        postIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        postIdField.setForeground(new Color(40, 40, 40));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(postIdField, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 250));

        backButton = new JButton("← Cancel");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(240, 240, 240));
        backButton.setForeground(new Color(60, 60, 60));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backButton.setPreferredSize(new Dimension(120, 35));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            } else {
                new AdminDashboard();
            }
            dispose();
        });

        deleteButton = new JButton("🗑️ Delete News");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(255, 200, 200));
        deleteButton.setForeground(new Color(180, 0, 0));
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(198, 40, 40), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        deleteButton.setPreferredSize(new Dimension(150, 35));
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String postId = postIdField.getText().trim();
                if (postId.isEmpty()) {
                    JOptionPane.showMessageDialog(DeleteNewsScreen.this, 
                        "Please enter a News ID!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirmation = JOptionPane.showConfirmDialog(
                    DeleteNewsScreen.this,
                    "Are you sure you want to delete news with ID: " + postId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    NewsControllerView newsController = new NewsControllerView();
                    boolean success = newsController.deleteNews(postId);
                    if (success) {
                        JOptionPane.showMessageDialog(DeleteNewsScreen.this, 
                            "News deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        postIdField.setText("");
                        
                        // Refresh previous screen if it's DisplayNewsScreen
                        if (previousScreen instanceof DisplayNewsScreen) {
                            ((DisplayNewsScreen) previousScreen).refreshUI();
                        }
                    } else {
                        JOptionPane.showMessageDialog(DeleteNewsScreen.this, 
                            "Failed to delete news. News ID may not exist.", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}



