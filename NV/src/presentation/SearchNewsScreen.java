package src.presentation;

import src.controllers.NewsControllerView;
import src.business.News;
import src.technical.ImageUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Search News Screen for users
 */
public class SearchNewsScreen extends JFrame {
    private NewsControllerView newsController;
    private JPanel resultsPanel;
    private JFrame previousScreen;

    public SearchNewsScreen(JFrame previousScreen) {
        this.previousScreen = previousScreen;
        newsController = new NewsControllerView();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("NewsVine - Search News");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Search News");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBackground(new Color(240, 248, 255));
        backButton.setForeground(new Color(70, 130, 180));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
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
        headerPanel.add(backButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        searchPanel.setBackground(new Color(245, 245, 250));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createTitledBorder("Enter search term (title, content, or category)"));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("🔍 Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(200, 220, 255));
        searchButton.setForeground(new Color(50, 80, 120));
        searchButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        searchButton.setPreferredSize(new Dimension(120, 40));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (searchTerm.isEmpty()) {
                loadAllNews();
            } else {
                performSearch(searchTerm);
            }
        });
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // Results Panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(new Color(245, 245, 250));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        loadAllNews();
        setVisible(true);
    }

    private void loadAllNews() {
        displayNews(newsController.getAllNews());
    }

    private void performSearch(String searchTerm) {
        List<News> results = newsController.searchNews(searchTerm);
        displayNews(results);
    }

    private void displayNews(List<News> newsList) {
        resultsPanel.removeAll();

        if (newsList.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No news articles found.");
            noResultsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noResultsLabel.setForeground(new Color(80, 80, 80)); // Better contrast
            noResultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultsPanel.add(noResultsLabel);
        } else {
            for (News news : newsList) {
                resultsPanel.add(createNewsCard(news));
                resultsPanel.add(Box.createVerticalStrut(15));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createNewsCard(News news) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(new Color(255, 255, 255));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        // Image (if available)
        if (news.getImageURL() != null && !news.getImageURL().trim().isEmpty()) {
            ImageIcon imageIcon = ImageUtil.loadImageIcon(news.getImageURL(), 500, 250);
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            card.add(imageLabel, BorderLayout.NORTH);
        }

        JLabel titleLabel = new JLabel("<html><div style='color: #1a1a1a;'><b>" + 
            escapeHtml(news.getTitle()) + "</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 19));
        titleLabel.setForeground(new Color(26, 26, 26));
        card.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(news.getContent());
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setForeground(new Color(40, 40, 40));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(new Color(250, 250, 250));
        contentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.add(contentArea, BorderLayout.CENTER);

        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        metaPanel.setBackground(Color.WHITE);
        if (news.getAuthorName() != null) {
            JLabel authorLabel = new JLabel("👤 " + news.getAuthorName());
            authorLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            authorLabel.setForeground(new Color(70, 70, 70));
            metaPanel.add(authorLabel);
        }
        if (news.getCreatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
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
        card.add(metaPanel, BorderLayout.SOUTH);

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

