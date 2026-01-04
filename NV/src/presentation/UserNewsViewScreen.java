package src.presentation;

import src.controllers.NewsControllerView;
import src.controllers.CommentController;
import src.business.News;
import src.business.Comment;
import src.technical.ImageUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * User view screen for viewing news and adding comments
 */
public class UserNewsViewScreen extends JFrame {
    private NewsControllerView newsController;
    private CommentController commentController;
    private JPanel newsPanel;
    private JFrame previousScreen;

    public UserNewsViewScreen(JFrame previousScreen) {
        this.previousScreen = previousScreen;
        newsController = new NewsControllerView();
        commentController = new CommentController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("NewsVine - View News");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header with better contrast
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(46, 125, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("📰 All News");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 255, 255)); // Pure white for better contrast
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Category Filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setBackground(new Color(46, 125, 50));
        
        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        filterLabel.setForeground(Color.WHITE);
        filterPanel.add(filterLabel);
        
        JComboBox<String> categoryFilter = new JComboBox<>(new String[]{"All", "General", "Technology", "Sports", "Politics", "Entertainment"});
        categoryFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryFilter.addActionListener(e -> {
            String selected = (String) categoryFilter.getSelectedItem();
            if ("All".equals(selected)) {
                loadNews();
            } else {
                loadNewsByCategory(selected);
            }
        });
        filterPanel.add(categoryFilter);
        
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBackground(new Color(240, 255, 240));
        backButton.setForeground(new Color(46, 125, 50));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 125, 50), 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            } else {
                new UserDashboard();
            }
            dispose();
        });
        filterPanel.add(backButton);
        
        headerPanel.add(filterPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // News Panel with better background
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        newsPanel.setBackground(new Color(240, 242, 245)); // Better contrast background
        JScrollPane scrollPane = new JScrollPane(newsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling
        add(scrollPane, BorderLayout.CENTER);

        loadNews();
        setVisible(true);
    }
    
    private void loadNewsByCategory(String category) {
        newsPanel.removeAll();
        List<News> newsList = newsController.getNewsByCategory(category);

        if (newsList.isEmpty()) {
            JLabel noNewsLabel = new JLabel("No news articles in this category.");
            noNewsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noNewsLabel.setForeground(new Color(80, 80, 80)); // Better contrast
            noNewsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            newsPanel.add(noNewsLabel);
        } else {
            for (News news : newsList) {
                newsPanel.add(createNewsCard(news));
                newsPanel.add(Box.createVerticalStrut(20));
            }
        }

        newsPanel.revalidate();
        newsPanel.repaint();
    }

    private void loadNews() {
        newsPanel.removeAll();
        List<News> newsList = newsController.getAllNews();

        if (newsList.isEmpty()) {
            JLabel noNewsLabel = new JLabel("No news articles available.");
            noNewsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noNewsLabel.setForeground(new Color(80, 80, 80)); // Better contrast
            noNewsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            newsPanel.add(noNewsLabel);
        } else {
            for (News news : newsList) {
                newsPanel.add(createNewsCard(news));
                newsPanel.add(Box.createVerticalStrut(15));
            }
        }

        newsPanel.revalidate();
        newsPanel.repaint();
    }

    private JPanel createNewsCard(News news) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(new Color(255, 255, 255));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));

        // Image/Video Panel
        JPanel mediaPanel = new JPanel(new BorderLayout());
        mediaPanel.setBackground(Color.WHITE);
        mediaPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        boolean hasMedia = false;
        
        // Display Image if available
        if (news.getImageURL() != null && !news.getImageURL().trim().isEmpty()) {
            ImageIcon imageIcon = ImageUtil.loadImageIcon(news.getImageURL(), 600, 350);
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mediaPanel.add(imageLabel, BorderLayout.CENTER);
            hasMedia = true;
        }
        
        // Display Video Thumbnail if video URL exists (and no image)
        if (!hasMedia && news.getVideoURL() != null && !news.getVideoURL().trim().isEmpty()) {
            // Use video URL to get thumbnail (YouTube or other)
            ImageIcon videoThumbnail = ImageUtil.loadImageIcon(news.getVideoURL(), 600, 350);
            JPanel videoContainer = new JPanel(new BorderLayout());
            videoContainer.setBackground(Color.WHITE);
            JLabel videoLabel = new JLabel(videoThumbnail);
            videoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            videoContainer.add(videoLabel, BorderLayout.CENTER);
            
            // Add play button indicator
            JLabel playIndicator = new JLabel("▶ VIDEO", SwingConstants.CENTER);
            playIndicator.setFont(new Font("Arial", Font.BOLD, 20));
            playIndicator.setForeground(new Color(255, 255, 255));
            playIndicator.setBackground(new Color(0, 0, 0, 150));
            playIndicator.setOpaque(true);
            playIndicator.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            videoContainer.add(playIndicator, BorderLayout.SOUTH);
            
            mediaPanel.add(videoContainer, BorderLayout.CENTER);
            hasMedia = true;
        }
        
        if (hasMedia) {
            card.add(mediaPanel, BorderLayout.NORTH);
        }

        // Title and Author Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("<html><div style='color: #1a1a1a;'><b>" + 
            escapeHtml(news.getTitle()) + "</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 26, 26)); // Dark text for better contrast
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        metaPanel.setBackground(Color.WHITE);
        
        if (news.getAuthorName() != null) {
            JLabel authorLabel = new JLabel("👤 " + news.getAuthorName());
            authorLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            authorLabel.setForeground(new Color(70, 70, 70)); // Better contrast
            metaPanel.add(authorLabel);
        }
        if (news.getCreatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm");
            JLabel dateLabel = new JLabel("📅 " + sdf.format(news.getCreatedAt()));
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            dateLabel.setForeground(new Color(70, 70, 70));
            metaPanel.add(dateLabel);
        }
        if (news.getNewsCategory() != null && !news.getNewsCategory().isEmpty()) {
            JLabel categoryLabel = new JLabel("🏷️ " + news.getNewsCategory());
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 13));
            categoryLabel.setForeground(new Color(46, 125, 50));
            categoryLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 125, 50), 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
            ));
            metaPanel.add(categoryLabel);
        }
        headerPanel.add(metaPanel, BorderLayout.SOUTH);
        card.add(headerPanel, BorderLayout.CENTER);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        
        JTextArea contentArea = new JTextArea(news.getContent());
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 15));
        contentArea.setForeground(new Color(40, 40, 40)); // Dark text for better readability
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(new Color(250, 250, 250));
        contentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        contentPanel.add(contentArea, BorderLayout.CENTER);
        
        // Video Link Panel (if video URL exists)
        if (news.getVideoURL() != null && !news.getVideoURL().trim().isEmpty()) {
            JPanel videoLinkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            videoLinkPanel.setBackground(Color.WHITE);
            videoLinkPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            
            JButton videoLinkButton = new JButton("🎥 Watch Video");
            videoLinkButton.setFont(new Font("Arial", Font.BOLD, 13));
            videoLinkButton.setBackground(new Color(255, 0, 0));
            videoLinkButton.setForeground(Color.WHITE);
            videoLinkButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 0, 0), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
            ));
            videoLinkButton.setFocusPainted(false);
            videoLinkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            videoLinkButton.addActionListener(e -> {
                try {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(news.getVideoURL()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserNewsViewScreen.this, 
                        "Could not open video link: " + news.getVideoURL(), "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            videoLinkPanel.add(videoLinkButton);
            contentPanel.add(videoLinkPanel, BorderLayout.SOUTH);
        }
        
        card.add(contentPanel, BorderLayout.CENTER);

        // Comments Section
        JPanel commentsPanel = createCommentsPanel(news.getNewsId());
        card.add(commentsPanel, BorderLayout.SOUTH);

        return card;
    }
    
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }

    private JPanel createCommentsPanel(int newsId) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "💬 Comments",
                0, 0,
                new Font("Arial", Font.BOLD, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Comments List
        JPanel commentsList = new JPanel();
        commentsList.setLayout(new BoxLayout(commentsList, BoxLayout.Y_AXIS));
        commentsList.setBackground(new Color(248, 249, 250));

        List<Comment> comments = commentController.getCommentsByNewsId(newsId);
        if (comments.isEmpty()) {
            JLabel noCommentsLabel = new JLabel("No comments yet. Be the first to comment!");
            noCommentsLabel.setFont(new Font("Arial", Font.ITALIC, 13));
            noCommentsLabel.setForeground(new Color(120, 120, 120)); // Better contrast
            noCommentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            commentsList.add(noCommentsLabel);
        } else {
            for (Comment comment : comments) {
                JPanel commentPanel = new JPanel(new BorderLayout());
                commentPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                commentPanel.setBackground(new Color(255, 255, 255));

                JPanel commentHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                commentHeader.setBackground(Color.WHITE);
                JLabel userNameLabel = new JLabel("<html><b style='color: #2e7d32;'>" + 
                    escapeHtml(comment.getUserName()) + "</b></html>");
                userNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
                commentHeader.add(userNameLabel);
                
                if (comment.getCreatedAt() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm");
                    JLabel timeLabel = new JLabel(" • " + sdf.format(comment.getCreatedAt()));
                    timeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                    timeLabel.setForeground(new Color(120, 120, 120));
                    commentHeader.add(timeLabel);
                }
                
                JLabel commentLabel = new JLabel("<html><div style='color: #333333; margin-top: 5px;'>" + 
                    escapeHtml(comment.getComment()) + "</div></html>");
                commentLabel.setFont(new Font("Arial", Font.PLAIN, 13));
                commentLabel.setForeground(new Color(51, 51, 51)); // Dark text for better readability

                JPanel commentContent = new JPanel(new BorderLayout());
                commentContent.setBackground(Color.WHITE);
                commentContent.add(commentHeader, BorderLayout.NORTH);
                commentContent.add(commentLabel, BorderLayout.CENTER);
                
                commentPanel.add(commentContent, BorderLayout.CENTER);

                commentsList.add(commentPanel);
                commentsList.add(Box.createVerticalStrut(8));
            }
        }

        JScrollPane commentsScroll = new JScrollPane(commentsList);
        commentsScroll.setPreferredSize(new Dimension(0, 120));
        commentsScroll.setBorder(BorderFactory.createEmptyBorder());
        commentsScroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(commentsScroll, BorderLayout.CENTER);

        // Add Comment Section
        JPanel addCommentPanel = new JPanel(new BorderLayout());
        addCommentPanel.setBackground(new Color(248, 249, 250));
        addCommentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JTextField commentField = new JTextField();
        commentField.setFont(new Font("Arial", Font.PLAIN, 13));
        commentField.setForeground(new Color(40, 40, 40));
        commentField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        commentField.setToolTipText("Type your comment here...");
        addCommentPanel.add(commentField, BorderLayout.CENTER);

        JButton addCommentButton = new JButton("💬 Post Comment");
        addCommentButton.setFont(new Font("Arial", Font.BOLD, 13));
        addCommentButton.setBackground(new Color(200, 255, 200));
        addCommentButton.setForeground(new Color(0, 100, 0));
        addCommentButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 125, 50), 2),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        addCommentButton.setFocusPainted(false);
        addCommentButton.setPreferredSize(new Dimension(140, 35));
        addCommentButton.addActionListener(e -> {
            String commentText = commentField.getText().trim();
            if (!commentText.isEmpty()) {
                boolean success = commentController.addComment(newsId, commentText);
                if (success) {
                    commentField.setText("");
                    loadNews(); // Reload to show new comment
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a comment!", "Empty Comment", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        addCommentPanel.add(addCommentButton, BorderLayout.EAST);

        panel.add(addCommentPanel, BorderLayout.SOUTH);

        return panel;
    }
}

