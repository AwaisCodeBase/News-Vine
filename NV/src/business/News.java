package src.business;

import java.sql.Timestamp;

/**
 * News entity class with author support
 */
public class News {

    private int news_id;
    private String title;
    private String content;
    private String news_category;
    private String imageURL;
    private String videoURL;
    private int author_id;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String author_name; // For display purposes

    // Constructor for creating new news
    public News(String title, String content, String news_category, String imageURL, String videoURL, int author_id) {
        this.title = title;
        this.content = content;
        this.news_category = news_category;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
        this.author_id = author_id;
    }

    // Constructor for loading from database
    public News(int news_id, String title, String content, String news_category, String imageURL, String videoURL, int author_id, Timestamp created_at, Timestamp updated_at) {
        this.news_id = news_id;
        this.title = title;
        this.content = content;
        this.news_category = news_category;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
        this.author_id = author_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Constructor with author name for display
    public News(int news_id, String title, String content, String news_category, String imageURL, String videoURL, int author_id, Timestamp created_at, Timestamp updated_at, String author_name) {
        this.news_id = news_id;
        this.title = title;
        this.content = content;
        this.news_category = news_category;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
        this.author_id = author_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.author_name = author_name;
    }

    // Legacy constructor for backward compatibility
    public News(int news_id, String title, String content, String news_category, String imageURL, String videoURL) {
        this.news_id = news_id;
        this.title = title;
        this.content = content;
        this.news_category = news_category;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
    }

    // Getters
    public int getNewsId() { 
        return news_id; 
    }

    public String getContent() {
        return content;
    }

    public String getTitle() { 
        return title; 
    }

    public String getNewsCategory() { 
        return news_category; 
    }

    public String getImageURL() { 
        return imageURL; 
    }

    public String getVideoURL() { 
        return videoURL; 
    }

    public int getAuthorId() {
        return author_id;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public Timestamp getUpdatedAt() {
        return updated_at;
    }

    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(String author_name) {
        this.author_name = author_name;
    }

    // Setters
    public void setNewsId(int news_id) {
        this.news_id = news_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNewsCategory(String news_category) {
        this.news_category = news_category;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}









//Final Form





// package src.business;

// public class News {

//     private int newsId;
//     private String title;
//     private String content;
//     private String newsCategory;
//     private String imageURL;
//     private String videoURL;
//     private String comment;

//     public News(int newsId, String title, String content, String newsCategory, String imageURL, String videoURL) {
//         this.newsId = newsId;
//         this.title = title;
//         this.content = content;
//         this.newsCategory = newsCategory;
//         this.imageURL = imageURL;
//         this.videoURL = videoURL;
//     }

//     public int getNewsId() {
//         return newsId;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public String getContent() {
//         return content;
//     }

//     public String getNewsCategory() {
//         return newsCategory;
//     }

//     public String getImageURL() {
//         return imageURL;
//     }

//     public String getVideoURL() {
//         return videoURL;
//     }

//     public String getComment() {
//         return comment;
//     }

//     public void setComment(String comment) {
//         this.comment = comment;
//     }
// }

