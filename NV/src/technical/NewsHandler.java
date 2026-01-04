package src.technical;

import src.business.News;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database handler for News operations with author support and search/sort
 */
public class NewsHandler {

    /**
     * Add a new news post
     */
    public boolean addNews(News news) {
        String insertSQL = "INSERT INTO news (title, news_category, content, imageURL, videoURL, author_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, news.getTitle());
            pstmt.setString(2, news.getNewsCategory());
            pstmt.setString(3, news.getContent());
            pstmt.setString(4, news.getImageURL());
            pstmt.setString(5, news.getVideoURL());
            pstmt.setInt(6, news.getAuthorId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        news.setNewsId(generatedKeys.getInt(1));
                    }
                }
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a news post
     */
    public boolean deleteNews(int newsId) {
        String query = "DELETE FROM news WHERE news_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newsId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update a news post
     */
    public boolean updateNews(News news) {
        String query = "UPDATE news SET title = ?, content = ?, news_category = ?, imageURL = ?, videoURL = ? WHERE news_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getContent());
            statement.setString(3, news.getNewsCategory());
            statement.setString(4, news.getImageURL());
            statement.setString(5, news.getVideoURL());
            statement.setInt(6, news.getNewsId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all news posts with author information, ordered by date (newest first)
     */
    public List<News> getAllNews() {
        return getAllNews("created_at", "DESC");
    }

    /**
     * Get all news posts with sorting options
     */
    public List<News> getAllNews(String sortBy, String order) {
        String query = "SELECT n.news_id, n.title, n.content, n.news_category, n.imageURL, n.videoURL, " +
                       "n.author_id, n.created_at, n.updated_at, u.user_name " +
                       "FROM news n " +
                       "JOIN users u ON n.author_id = u.user_id " +
                       "ORDER BY n." + sortBy + " " + order;
        List<News> newsList = new ArrayList<>();
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                News news = new News(
                    resultSet.getInt("news_id"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getString("news_category"),
                    resultSet.getString("imageURL"),
                    resultSet.getString("videoURL"),
                    resultSet.getInt("author_id"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at"),
                    resultSet.getString("user_name")
                );
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    /**
     * Search news by title or category
     */
    public List<News> searchNews(String searchTerm) {
        String query = "SELECT n.news_id, n.title, n.content, n.news_category, n.imageURL, n.videoURL, " +
                       "n.author_id, n.created_at, n.updated_at, u.user_name " +
                       "FROM news n " +
                       "JOIN users u ON n.author_id = u.user_id " +
                       "WHERE n.title LIKE ? OR n.content LIKE ? OR n.news_category LIKE ? " +
                       "ORDER BY n.created_at DESC";
        List<News> newsList = new ArrayList<>();
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    News news = new News(
                        resultSet.getInt("news_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("news_category"),
                        resultSet.getString("imageURL"),
                        resultSet.getString("videoURL"),
                        resultSet.getInt("author_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        resultSet.getString("user_name")
                    );
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    /**
     * Get news by ID
     */
    public News getNewsById(int newsId) {
        String query = "SELECT n.news_id, n.title, n.content, n.news_category, n.imageURL, n.videoURL, " +
                       "n.author_id, n.created_at, n.updated_at, u.user_name " +
                       "FROM news n " +
                       "JOIN users u ON n.author_id = u.user_id " +
                       "WHERE n.news_id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, newsId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new News(
                        resultSet.getInt("news_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("news_category"),
                        resultSet.getString("imageURL"),
                        resultSet.getString("videoURL"),
                        resultSet.getInt("author_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        resultSet.getString("user_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get news by category
     */
    public List<News> getNewsByCategory(String category) {
        String query = "SELECT n.news_id, n.title, n.content, n.news_category, n.imageURL, n.videoURL, " +
                       "n.author_id, n.created_at, n.updated_at, u.user_name " +
                       "FROM news n " +
                       "JOIN users u ON n.author_id = u.user_id " +
                       "WHERE n.news_category = ? " +
                       "ORDER BY n.created_at DESC";
        List<News> newsList = new ArrayList<>();
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    News news = new News(
                        resultSet.getInt("news_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("news_category"),
                        resultSet.getString("imageURL"),
                        resultSet.getString("videoURL"),
                        resultSet.getInt("author_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        resultSet.getString("user_name")
                    );
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
