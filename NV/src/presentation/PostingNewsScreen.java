// package src.presentation;

// import javax.swing.*;

// import src.controllers.NewsControllerView;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.Random;

// public class PostingNewsScreen extends JFrame {

//     private JTextField NewsIdField,titleField, contentField, categoryField, imageURLField, videoURLField;
//     private JButton postButton;

//     public PostingNewsScreen() {
//         setTitle("Post News");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new GridLayout(6, 2));

//         JLabel NewsIdLabel = new JLabel("News Id:");
//         NewsIdField = new JTextField(Integer.toString(new Random().nextInt(9000) + 1000));
//         add(NewsIdLabel);
//         add(NewsIdField);
//         JLabel titleLabel = new JLabel("Title:");
//         titleField = new JTextField();
//         add(titleLabel);
//         add(titleField);

//         JLabel contentLabel = new JLabel("Content:");
//         contentField = new JTextField();
//         add(contentLabel);
//         add(contentField);

//         JLabel categoryLabel = new JLabel("Category:");
//         categoryField = new JTextField();
//         add(categoryLabel);
//         add(categoryField);

//         JLabel imageURLLabel = new JLabel("Image URL:");
//         imageURLField = new JTextField();
//         add(imageURLLabel);
//         add(imageURLField);

//         JLabel videoURLLabel = new JLabel("Video URL:");
//         videoURLField = new JTextField();
//         add(videoURLLabel);
//         add(videoURLField);

//         postButton = new JButton("Post");
//         postButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 NewsControllerView newsController = new NewsControllerView();
//                 try {
//                     boolean successMessage =newsController.addnews(
//                         NewsIdField.getText(),
//                             titleField.getText(),
//                             contentField.getText(),
//                             categoryField.getText(),
//                             imageURLField.getText(),
//                             videoURLField.getText()
//                     );

//                     if (successMessage) {
//                         JOptionPane.showMessageDialog(PostingNewsScreen.this, "News posted successfully!");
//                     } else {
//                         JOptionPane.showMessageDialog(PostingNewsScreen.this, "Failed to post news!");
//                     }
//                 } catch (Exception ex) {
//                     ex.printStackTrace();
//                     JOptionPane.showMessageDialog(PostingNewsScreen.this, "An error occurred while posting news!");
//                 }
//             }
//         });
//         add(postButton);

//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new PostingNewsScreen();
//             }
//         });
//     }
// }


// package src.presentation;

// import javax.swing.*;
// import src.controllers.NewsControllerView;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.Random;

// public class PostingNewsScreen extends JFrame {

//     private JTextField newsIdField, titleField, contentField, categoryField, imageURLField, videoURLField;
//     private JButton postButton, backButton;
//     private JFrame previousScreen;

//     public PostingNewsScreen(JFrame previousScreen) {
//         this.previousScreen = previousScreen;
//         setTitle("Post News");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new GridLayout(7, 2)); // 7 rows for an additional back button

//         JLabel newsIdLabel = new JLabel("News Id:");
//         newsIdField = new JTextField(Integer.toString(new Random().nextInt(9000) + 1000));
//         add(newsIdLabel);
//         add(newsIdField);

//         JLabel titleLabel = new JLabel("Title:");
//         titleField = new JTextField();
//         add(titleLabel);
//         add(titleField);

//         JLabel contentLabel = new JLabel("Content:");
//         contentField = new JTextField();
//         add(contentLabel);
//         add(contentField);

//         JLabel categoryLabel = new JLabel("Category:");
//         categoryField = new JTextField();
//         add(categoryLabel);
//         add(categoryField);

//         JLabel imageURLLabel = new JLabel("Image URL:");
//         imageURLField = new JTextField();
//         add(imageURLLabel);
//         add(imageURLField);

//         JLabel videoURLLabel = new JLabel("Video URL:");
//         videoURLField = new JTextField();
//         add(videoURLLabel);
//         add(videoURLField);

//         postButton = new JButton("Post");
//         postButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 NewsControllerView newsController = new NewsControllerView();
//                 try {
//                     boolean successMessage = newsController.addnews(
//                         newsIdField.getText(),
//                         titleField.getText(),
//                         contentField.getText(),
//                         categoryField.getText(),
//                         imageURLField.getText(),
//                         videoURLField.getText()
//                     );

//                     if (successMessage) {
//                         JOptionPane.showMessageDialog(PostingNewsScreen.this, "News posted successfully!");
//                     } else {
//                         JOptionPane.showMessageDialog(PostingNewsScreen.this, "Failed to post news!");
//                     }
//                 } catch (Exception ex) {
//                     ex.printStackTrace();
//                     JOptionPane.showMessageDialog(PostingNewsScreen.this, "An error occurred while posting news!");
//                 }
//             }
//         });
//         add(postButton);

//         backButton = new JButton("Back");
//         backButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 previousScreen.setVisible(true);
//                 dispose();
//             }
//         });
//         add(backButton);

//         setVisible(true);
//     }
// }









package src.presentation;

import javax.swing.*;
import src.controllers.NewsControllerView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostingNewsScreen extends JFrame {

    private JTextField titleField;
    private JTextArea contentArea;
    private JTextField categoryField, imageURLField, videoURLField;
    private JButton postButton, backButton;
    private JFrame previousScreen;

    public PostingNewsScreen(JFrame previousScreen) {
        this.previousScreen = previousScreen;
        setTitle("Post News - Admin");
        setSize(600, 500);
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

        // Title
        JLabel titleLabel = new JLabel("Title:*");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        titleField = new JTextField(30);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleField, gbc);

        // Content
        JLabel contentLabel = new JLabel("Content:*");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(contentLabel, gbc);

        contentArea = new JTextArea(8, 30);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(contentScroll, gbc);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(categoryLabel, gbc);

        categoryField = new JTextField(30);
        categoryField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(categoryField, gbc);

        // Image URL
        JLabel imageURLLabel = new JLabel("Image URL:");
        imageURLLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(imageURLLabel, gbc);

        imageURLField = new JTextField(30);
        imageURLField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(imageURLField, gbc);

        // Video URL
        JLabel videoURLLabel = new JLabel("Video URL:");
        videoURLLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(videoURLLabel, gbc);

        videoURLField = new JTextField(30);
        videoURLField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(videoURLField, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 250));

        postButton = new JButton("Post News");
        postButton.setFont(new Font("Arial", Font.BOLD, 14));
        postButton.setBackground(new Color(70, 130, 180));
        postButton.setForeground(Color.WHITE);
        postButton.setPreferredSize(new Dimension(120, 35));
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    NewsControllerView newsController = new NewsControllerView();
                    boolean success = newsController.addNews(
                        titleField.getText().trim(),
                        contentArea.getText().trim(),
                        categoryField.getText().trim(),
                        imageURLField.getText().trim(),
                        videoURLField.getText().trim()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(PostingNewsScreen.this, "News posted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    }
                }
            }
        });

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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (previousScreen != null) {
                    previousScreen.setVisible(true);
                } else {
                    new AdminDashboard();
                }
                dispose();
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(postButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private boolean validateFields() {
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (contentArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearFields() {
        titleField.setText("");
        contentArea.setText("");
        categoryField.setText("");
        imageURLField.setText("");
        videoURLField.setText("");
    }
}










