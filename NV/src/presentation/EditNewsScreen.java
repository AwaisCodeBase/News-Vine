// package src.presentation;
// import javax.swing.*;

// import src.controllers.NewsControllerView;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class EditNewsScreen extends JFrame {

//     private JTextField postIdField, titleField, contentField, categoryField, imageURLField, videoURLField;
//     private JButton editButton;

//     public EditNewsScreen() {
//         setTitle("Edit News");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new GridLayout(7, 2));

//         JLabel postIdLabel = new JLabel("Post ID:");
//         postIdField = new JTextField();
//         add(postIdLabel);
//         add(postIdField);

//         JLabel titleLabel = new JLabel("New Title:");
//         titleField = new JTextField();
//         add(titleLabel);
//         add(titleField);

//         JLabel contentLabel = new JLabel("New Content:");
//         contentField = new JTextField();
//         add(contentLabel);
//         add(contentField);

//         JLabel categoryLabel = new JLabel("New Category:");
//         categoryField = new JTextField();
//         add(categoryLabel);
//         add(categoryField);

//         JLabel imageURLLabel = new JLabel("New Image URL:");
//         imageURLField = new JTextField();
//         add(imageURLLabel);
//         add(imageURLField);

//         JLabel videoURLLabel = new JLabel("New Video URL:");
//         videoURLField = new JTextField();
//         add(videoURLLabel);
//         add(videoURLField);

//         editButton = new JButton("Edit");
//         editButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 int postId = Integer.parseInt(postIdField.getText());
//                 String title = titleField.getText();
//                 String content = contentField.getText();
//                 String category = categoryField.getText();
//                 String imageURL = imageURLField.getText();
//                 String videoURL = videoURLField.getText();

//                 NewsControllerView newsController = new NewsControllerView();
//                 boolean success = newsController.editnews(postId, title, content, category, imageURL, videoURL);
//                 if (success) {
//                     JOptionPane.showMessageDialog(EditNewsScreen.this, "News edited successfully!");
//                 } else {
//                     JOptionPane.showMessageDialog(EditNewsScreen.this, "Failed to edit news.");
//                 }
//             }
//         });
//         add(editButton);

//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new EditNewsScreen();
//             }
//         });
//     }
// }










package src.presentation;

import javax.swing.*;
import src.controllers.NewsControllerView;
import src.business.News;
import java.awt.*;

/**
 * Edit News Screen with proper UI and error handling
 */
public class EditNewsScreen extends JFrame {
    private JTextField titleField;
    private JTextArea contentArea;
    private JTextField categoryField, imageURLField, videoURLField;
    private JButton saveButton, backButton;
    private News news;
    private JFrame previousScreen;

    public EditNewsScreen(JFrame previousScreen, News news) {
        try {
            if (news == null) {
                JOptionPane.showMessageDialog(null, "Invalid news article! News object is null.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
                if (previousScreen != null) {
                    previousScreen.setVisible(true);
                }
                dispose();
                return;
            }

            // Validate news has required fields
            if (news.getNewsId() <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid news ID! Cannot edit this article.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
                if (previousScreen != null) {
                    previousScreen.setVisible(true);
                }
                dispose();
                return;
            }

            this.news = news;
            this.previousScreen = previousScreen;

            initializeUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening edit screen: " + e.getMessage(), "Error", 
                JOptionPane.ERROR_MESSAGE);
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            }
            dispose();
        }
    }

    private void initializeUI() {
        setTitle("Edit News - Admin");
        setSize(700, 600);
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
        titleLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        titleField = new JTextField(news.getTitle() != null ? news.getTitle() : "", 40);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setForeground(new Color(40, 40, 40));
        titleField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleField, gbc);

        // Content
        JLabel contentLabel = new JLabel("Content:*");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contentLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(contentLabel, gbc);

        contentArea = new JTextArea(news.getContent() != null ? news.getContent() : "", 8, 40);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setForeground(new Color(40, 40, 40));
        contentArea.setBackground(Color.WHITE);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(contentScroll, gbc);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoryLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(categoryLabel, gbc);

        categoryField = new JTextField(news.getNewsCategory() != null ? news.getNewsCategory() : "", 40);
        categoryField.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryField.setForeground(new Color(40, 40, 40));
        categoryField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(categoryField, gbc);

        // Image URL
        JLabel imageURLLabel = new JLabel("Image URL:");
        imageURLLabel.setFont(new Font("Arial", Font.BOLD, 14));
        imageURLLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(imageURLLabel, gbc);

        imageURLField = new JTextField(news.getImageURL() != null ? news.getImageURL() : "", 40);
        imageURLField.setFont(new Font("Arial", Font.PLAIN, 14));
        imageURLField.setForeground(new Color(40, 40, 40));
        imageURLField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(imageURLField, gbc);

        // Video URL
        JLabel videoURLLabel = new JLabel("Video URL:");
        videoURLLabel.setFont(new Font("Arial", Font.BOLD, 14));
        videoURLLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(videoURLLabel, gbc);

        videoURLField = new JTextField(news.getVideoURL() != null ? news.getVideoURL() : "", 40);
        videoURLField.setFont(new Font("Arial", Font.PLAIN, 14));
        videoURLField.setForeground(new Color(40, 40, 40));
        videoURLField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(videoURLField, gbc);

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
            }
            dispose();
        });

        saveButton = new JButton("💾 Save Changes");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        saveButton.setPreferredSize(new Dimension(150, 35));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> handleSave());

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleSave() {
        // Validate fields
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty!", "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contentArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content cannot be empty!", "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            NewsControllerView newsController = new NewsControllerView();
            boolean success = newsController.editNews(
                news.getNewsId(),
                titleField.getText().trim(),
                contentArea.getText().trim(),
                categoryField.getText().trim(),
                imageURLField.getText().trim(),
                videoURLField.getText().trim()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "News updated successfully!", "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the previous screen if it has refreshUI method
                if (previousScreen instanceof DisplayNewsScreen) {
                    ((DisplayNewsScreen) previousScreen).refreshUI();
                }
                
                if (previousScreen != null) {
                    previousScreen.setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update news. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Empty constructor for compatibility (should not be used)
    public EditNewsScreen() {
        JOptionPane.showMessageDialog(null, "Please use EditNewsScreen(previousScreen, news) constructor!", 
            "Error", JOptionPane.ERROR_MESSAGE);
        dispose();
    }
}













// package src.presentation;

// import javax.swing.*;
// import src.controllers.NewsControllerView;
// import src.technical.NewsHandler;
// import src.business.News;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.List;

// public class EditNewsScreen extends JFrame {

//     private JTextField newsIdField, titleField, contentField, categoryField, imageURLField, videoURLField;
//     private JButton editButton, backButton, loadButton;
//     private NewsControllerView newsController;
//     private JFrame previousScreen;

//     public EditNewsScreen(JFrame previousScreen) {
//         this.previousScreen = previousScreen;
//         this.newsController = new NewsControllerView();

//         setTitle("Edit News");
//         setSize(400, 400);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new GridLayout(8, 2));

//         JLabel newsIdLabel = new JLabel("News Id:");
//         newsIdField = new JTextField();
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

//         loadButton = new JButton("Load News");
//         loadButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 loadNews();
//             }
//         });
//         add(loadButton);

//         editButton = new JButton("Edit");
//         editButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 editNews();
//             }
//         });
//         add(editButton);

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

//     private void loadNews() {
//         int newsId = Integer.parseInt(newsIdField.getText());
//         NewsHandler newsHandler = new NewsHandler();
//         News news = newsHandler.getNewsById(newsId);

//         if (news != null) {
//             titleField.setText(news.getTitle());
//             contentField.setText(news.getContent());
//             categoryField.setText(news.getNewsCategory());
//             imageURLField.setText(news.getImageURL());
//             videoURLField.setText(news.getVideoURL());
//         } else {
//             JOptionPane.showMessageDialog(this, "News not found!");
//         }
//     }

//     private void editNews() {
//         try {
//             int newsId = Integer.parseInt(newsIdField.getText());
//             boolean success = newsController.editNews(
//                 newsId,
//                 titleField.getText(),
//                 contentField.getText(),
//                 categoryField.getText(),
//                 imageURLField.getText(),
//                 videoURLField.getText()
//             );

//             if (success) {
//                 JOptionPane.showMessageDialog(this, "News edited successfully!");
//             } else {
//                 JOptionPane.showMessageDialog(this, "Failed to edit news!");
//             }
//         } catch (Exception ex) {
//             ex.printStackTrace();
//             JOptionPane.showMessageDialog(this, "An error occurred while editing news!");
//         }
//     }
// }






//Final Form



// package src.presentation;

// import javax.swing.*;
// import src.controllers.NewsControllerView;
// import src.technical.NewsHandler;
// import src.business.News;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class EditNewsScreen extends JFrame {

//     private JTextField newsIdField, titleField, contentField, categoryField, imageURLField, videoURLField;
//     private JButton editButton, backButton, loadButton;
//     private NewsControllerView newsController;
//     private JFrame previousScreen;

//     public EditNewsScreen(JFrame previousScreen) {
//         this.previousScreen = previousScreen;
//         this.newsController = new NewsControllerView();

//         setTitle("Edit News");
//         setSize(500, 400);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new GridBagLayout());

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.fill = GridBagConstraints.HORIZONTAL;
//         gbc.insets = new Insets(10, 10, 10, 10);

//         JLabel newsIdLabel = new JLabel("News Id:");
//         newsIdField = new JTextField();
//         gbc.gridx = 0; gbc.gridy = 0;
//         add(newsIdLabel, gbc);
//         gbc.gridx = 1; gbc.gridy = 0;
//         add(newsIdField, gbc);

//         JLabel titleLabel = new JLabel("Title:");
//         titleField = new JTextField();
//         gbc.gridx = 0; gbc.gridy = 1;
//         add(titleLabel, gbc);
//         gbc.gridx = 1; gbc.gridy = 1;
//         add(titleField, gbc);

//         JLabel contentLabel = new JLabel("Content:");
//         contentField = new JTextField();
//         gbc.gridx = 0; gbc.gridy = 2;
//         add(contentLabel, gbc);
//         gbc.gridx = 1; gbc.gridy = 2;
//         add(contentField, gbc);

//         JLabel categoryLabel = new JLabel("Category:");
//         categoryField = new JTextField();
//         gbc.gridx = 0; gbc.gridy = 3;
//         add(categoryLabel, gbc);
//         gbc.gridx = 1; gbc.gridy = 3;
//         add(categoryField, gbc);

//         JLabel imageURLLabel = new JLabel("Image URL:");
//         imageURLField = new JTextField();
//         gbc.gridx = 0; gbc.gridy = 4;
//         add(imageURLLabel, gbc);
//         gbc.gridx = 1; gbc.gridy = 4;
//         add(imageURLField, gbc);

//         JLabel videoURLLabel = new JLabel("Video URL:");
//         videoURLField = new JTextField();
//         gbc.gridx = 0; gbc.gridy = 5;
//         add(videoURLLabel, gbc);
//         gbc.gridx = 1; gbc.gridy = 5;
//         add(videoURLField, gbc);

//         loadButton = new JButton("Load News");
//         loadButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 loadNews();
//             }
//         });
//         gbc.gridx = 1; gbc.gridy = 6;
//         add(loadButton, gbc);

//         editButton = new JButton("Edit");
//         editButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 if (validateFields()) {
//                     editNews();
//                 }
//             }
//         });
//         gbc.gridx = 1; gbc.gridy = 7;
//         add(editButton, gbc);

//         backButton = new JButton("Back");
//         backButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 previousScreen.setVisible(true);
//                 dispose();
//             }
//         });
//         gbc.gridx = 0; gbc.gridy = 7;
//         add(backButton, gbc);

//         setVisible(true);
//     }

//     private void loadNews() {
//         try {
//             int newsId = Integer.parseInt(newsIdField.getText());
//             NewsHandler newsHandler = new NewsHandler();
//             News news = newsHandler.getNewsById(newsId);

//             if (news != null) {
//                 titleField.setText(news.getTitle());
//                 contentField.setText(news.getContent());
//                 categoryField.setText(news.getNewsCategory());
//                 imageURLField.setText(news.getImageURL());
//                 videoURLField.setText(news.getVideoURL());
//             } else {
//                 JOptionPane.showMessageDialog(this, "News not found!");
//             }
//         } catch (NumberFormatException e) {
//             JOptionPane.showMessageDialog(this, "Invalid news ID format!");
//         }
//     }

//     private void editNews() {
//         try {
//             int newsId = Integer.parseInt(newsIdField.getText());
//             boolean success = newsController.editNews(
//                 newsId,
//                 titleField.getText(),
//                 contentField.getText(),
//                 categoryField.getText(),
//                 imageURLField.getText(),
//                 videoURLField.getText()
//             );

//             if (success) {
//                 JOptionPane.showMessageDialog(this, "News edited successfully!");
//             } else {
//                 JOptionPane.showMessageDialog(this, "Failed to edit news!");
//             }
//         } catch (Exception ex) {
//             ex.printStackTrace();
//             JOptionPane.showMessageDialog(this, "An error occurred while editing news!");
//         }
//     }

//     private boolean validateFields() {
//         if (titleField.getText().trim().isEmpty() || contentField.getText().trim().isEmpty() ||
//             categoryField.getText().trim().isEmpty() || imageURLField.getText().trim().isEmpty() ||
//             videoURLField.getText().trim().isEmpty()) {
//             JOptionPane.showMessageDialog(this, "All fields must be filled out!");
//             return false;
//         }
//         return true;
//     }
// }





