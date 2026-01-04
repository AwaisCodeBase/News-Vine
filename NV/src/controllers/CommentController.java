package src.controllers;

import src.business.Comment;
import src.technical.CommentHandler;
import src.controllers.AuthController;

import javax.swing.*;
import java.util.List;

/**
 * Comment Controller with role-based access
 */
public class CommentController {

    private CommentHandler commentHandler;

    public CommentController() {
        this.commentHandler = new CommentHandler();
    }

    /**
     * Add a comment (Logged in users only)
     */
    public boolean addComment(int newsId, String commentText) {
        if (!AuthController.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login to add comments!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (commentText == null || commentText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Comment cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int userId = AuthController.getCurrentUser().getUserId();
        Comment comment = new Comment(0, newsId, userId, commentText.trim(), null);
        return commentHandler.addComment(comment);
    }

    /**
     * Get all comments for a news post
     */
    public List<Comment> getCommentsByNewsId(int newsId) {
        return commentHandler.getCommentsByNewsId(newsId);
    }

    /**
     * Delete a comment (Admin only)
     */
    public boolean deleteComment(int commentId) {
        if (!AuthController.isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only admins can delete comments!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return commentHandler.deleteComment(commentId);
    }
}

