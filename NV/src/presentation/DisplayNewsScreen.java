package src.presentation;

import src.controllers.NewsControllerView;
import src.business.News;
import src.technical.ImageUtil;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DisplayNewsScreen extends JFrame {
    private JButton backButton;
    private JPanel newsPanel;
    private JFrame previousScreen;
    private Timer timer;
    


    public DisplayNewsScreen(JFrame previousScreen) {
        this.previousScreen = previousScreen;
        setTitle("NewsVine - Admin News View");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("📰 All News Articles");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(70, 130, 180));
        buttonPanel.setOpaque(true);
        
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(new Color(70, 130, 180));
        refreshButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> refreshUI());
        buttonPanel.add(refreshButton);
        
        backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(70, 130, 180));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            } else {
                new AdminDashboard();
            }
            dispose();
        });
        buttonPanel.add(backButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // News Panel
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        newsPanel.setBackground(new Color(240, 242, 245));
        JScrollPane scrollPane = new JScrollPane(newsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    
        loadNews();
        setVisible(true);
    }
    
    public void refreshUI() {
        // Clear existing news entries
        newsPanel.removeAll();
    
        // Reload and display updated news
        loadNews();
    
        // Repaint the panel to reflect the changes
        newsPanel.revalidate();
        newsPanel.repaint();
    }
    
    // Make refreshUI accessible
    public void reloadNews() {
        refreshUI();
    }
    
    

    private void loadNews() {
        newsPanel.removeAll();
        NewsControllerView newsController = new NewsControllerView();
        List<News> newsList = newsController.getAllNews();

        if (newsList.isEmpty()) {
            JLabel noNewsLabel = new JLabel("No news articles available.");
            noNewsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noNewsLabel.setForeground(new Color(80, 80, 80));
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
    
    private JPanel createNewsCard(News news) {
        NewsControllerView newsController = new NewsControllerView();
        
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(new Color(255, 255, 255));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        // Image/Video Panel
        JPanel mediaPanel = new JPanel(new BorderLayout());
        mediaPanel.setBackground(Color.WHITE);
        mediaPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        boolean hasMedia = false;
        
        // Display Image if available
        if (news.getImageURL() != null && !news.getImageURL().trim().isEmpty() && !news.getImageURL().equals("null")) {
            try {
                System.out.println("Loading image from URL: " + news.getImageURL());
                ImageIcon imageIcon = ImageUtil.loadImageIcon(news.getImageURL(), 700, 400);
                if (imageIcon != null && imageIcon.getIconWidth() > 0) {
                    JLabel imageLabel = new JLabel(imageIcon);
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    mediaPanel.add(imageLabel, BorderLayout.CENTER);
                    hasMedia = true;
                    System.out.println("Image loaded successfully: " + imageIcon.getIconWidth() + "x" + imageIcon.getIconHeight());
                } else {
                    System.out.println("Image icon is null or invalid");
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                // Show placeholder
                JLabel errorLabel = new JLabel("⚠️ Image could not be loaded: " + news.getImageURL());
                errorLabel.setForeground(new Color(150, 150, 150));
                errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mediaPanel.add(errorLabel, BorderLayout.CENTER);
                hasMedia = true;
            }
        }
        
        // Display Video Thumbnail if video URL exists (and no image)
        if (!hasMedia && news.getVideoURL() != null && !news.getVideoURL().trim().isEmpty() && !news.getVideoURL().equals("null")) {
            try {
                System.out.println("Loading video thumbnail from URL: " + news.getVideoURL());
                ImageIcon videoThumbnail = ImageUtil.loadImageIcon(news.getVideoURL(), 700, 400);
                if (videoThumbnail != null && videoThumbnail.getIconWidth() > 0) {
                    JPanel videoContainer = new JPanel(new BorderLayout());
                    videoContainer.setBackground(Color.WHITE);
                    JLabel videoLabel = new JLabel(videoThumbnail);
                    videoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    videoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    videoContainer.add(videoLabel, BorderLayout.CENTER);
                    
                    // Add play indicator
                    JLabel playIndicator = new JLabel("▶ VIDEO", SwingConstants.CENTER);
                    playIndicator.setFont(new Font("Arial", Font.BOLD, 20));
                    playIndicator.setForeground(Color.WHITE);
                    playIndicator.setBackground(new Color(0, 0, 0, 150));
                    playIndicator.setOpaque(true);
                    playIndicator.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    videoContainer.add(playIndicator, BorderLayout.SOUTH);
                    
                    mediaPanel.add(videoContainer, BorderLayout.CENTER);
                    hasMedia = true;
                }
            } catch (Exception e) {
                System.err.println("Error loading video thumbnail: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (hasMedia) {
            card.add(mediaPanel, BorderLayout.NORTH);
        } else {
            // Show message if no media
            JLabel noMediaLabel = new JLabel("📷 No image or video available");
            noMediaLabel.setForeground(new Color(180, 180, 180));
            noMediaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noMediaLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noMediaLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            mediaPanel.add(noMediaLabel, BorderLayout.CENTER);
            card.add(mediaPanel, BorderLayout.NORTH);
        }

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("<html><div style='color: #1a1a1a;'><b>" + 
            escapeHtml(news.getTitle()) + "</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 26, 26));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        metaPanel.setBackground(Color.WHITE);
        
        if (news.getAuthorName() != null) {
            JLabel authorLabel = new JLabel("👤 " + news.getAuthorName());
            authorLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            authorLabel.setForeground(new Color(70, 70, 70));
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
            categoryLabel.setForeground(new Color(70, 130, 180));
            categoryLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
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
        contentArea.setForeground(new Color(40, 40, 40));
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
                    JOptionPane.showMessageDialog(DisplayNewsScreen.this, 
                        "Could not open video link: " + news.getVideoURL(), "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            videoLinkPanel.add(videoLinkButton);
            contentPanel.add(videoLinkPanel, BorderLayout.SOUTH);
        }
        
        card.add(contentPanel, BorderLayout.CENTER);

        // Action Buttons Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton editButton = new JButton("✏️ Edit");
        editButton.setFont(new Font("Arial", Font.BOLD, 13));
        editButton.setBackground(new Color(255, 235, 200));
        editButton.setForeground(new Color(200, 100, 0));
        editButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 152, 0), 2),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> {
            try {
                    new EditNewsScreen(DisplayNewsScreen.this, news);
                    setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(DisplayNewsScreen.this, 
                    "Error opening edit screen: " + ex.getMessage(), "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton deleteButton = new JButton("🗑️ Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 13));
        deleteButton.setBackground(new Color(255, 200, 200));
        deleteButton.setForeground(new Color(180, 0, 0));
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(198, 40, 40), 2),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(
                DisplayNewsScreen.this, 
                "Are you sure you want to delete this news article?",
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = newsController.deleteNews(String.valueOf(news.getNewsId()));
                if (success) {
                    JOptionPane.showMessageDialog(DisplayNewsScreen.this, 
                        "News deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshUI();
                } else {
                    JOptionPane.showMessageDialog(DisplayNewsScreen.this, 
                        "Failed to delete news!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        card.add(actionPanel, BorderLayout.SOUTH);

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
}

