package src.technical;

import src.business.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database handler for Comment operations
 */
public class CommentHandler {

    /**
     * Add a comment to a news post
     */
    public boolean addComment(Comment comment) {
        String insertSQL = "INSERT INTO comments (news_id, user_id, comment) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setInt(1, comment.getNewsId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getComment());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all comments for a news post
     */
    public List<Comment> getCommentsByNewsId(int newsId) {
        String query = "SELECT c.comment_id, c.news_id, c.user_id, c.comment, c.created_at, u.user_name " +
                       "FROM comments c " +
                       "JOIN users u ON c.user_id = u.user_id " +
                       "WHERE c.news_id = ? " +
                       "ORDER BY c.created_at ASC";
        List<Comment> comments = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newsId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment(
                        rs.getInt("comment_id"),
                        rs.getInt("news_id"),
                        rs.getInt("user_id"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at"),
                        rs.getString("user_name")
                    );
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    /**
     * Delete a comment (admin only)
     */
    public boolean deleteComment(int commentId) {
        String query = "DELETE FROM comments WHERE comment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, commentId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get comment by ID
     */
    public Comment getCommentById(int commentId) {
        String query = "SELECT c.comment_id, c.news_id, c.user_id, c.comment, c.created_at, u.user_name " +
                       "FROM comments c " +
                       "JOIN users u ON c.user_id = u.user_id " +
                       "WHERE c.comment_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, commentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Comment(
                        rs.getInt("comment_id"),
                        rs.getInt("news_id"),
                        rs.getInt("user_id"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at"),
                        rs.getString("user_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

