package src.business;

import java.sql.Timestamp;

/**
 * Comment entity class
 */
public class Comment {
    private int comment_id;
    private int news_id;
    private int user_id;
    private String comment;
    private Timestamp created_at;
    private String user_name; // For display purposes

    public Comment(int comment_id, int news_id, int user_id, String comment, Timestamp created_at) {
        this.comment_id = comment_id;
        this.news_id = news_id;
        this.user_id = user_id;
        this.comment = comment;
        this.created_at = created_at;
    }

    public Comment(int comment_id, int news_id, int user_id, String comment, Timestamp created_at, String user_name) {
        this.comment_id = comment_id;
        this.news_id = news_id;
        this.user_id = user_id;
        this.comment = comment;
        this.created_at = created_at;
        this.user_name = user_name;
    }

    // Getters
    public int getCommentId() {
        return comment_id;
    }

    public int getNewsId() {
        return news_id;
    }

    public int getUserId() {
        return user_id;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
}

