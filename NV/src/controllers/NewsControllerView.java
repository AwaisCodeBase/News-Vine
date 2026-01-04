// package src.controllers;
// import javax.swing.*;

// import src.business.News;
// import src.technical.NewsHandler;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// public class NewsControllerView extends JFrame {

    
//         public boolean addnews(String NewsId,String title, String content, String category, String imageURL, String videoURL) {
//             Integer news_id=Integer.parseInt(NewsId);
//             News news = new News(news_id,title,content,category,imageURL,videoURL);
//             String  Message=news.postNews(news_id,title, content, category, imageURL, videoURL);
//             if(Message=="Posted Successfully!")
//         return true;
//         else
//        return false;
//         }


//         // public boolean editnews(Integer postId, String newTitle, String newContent, String newCategory, String newImageURL, String newVideoURL) {
//         //     NewsHandler newsHandler = new NewsHandler();
//         //     return newsHandler.updateNews(postId, newTitle, newContent, newCategory, newImageURL, newVideoURL);
//         // }

//         public boolean editNewsURLs(int postId, String newImageURL, String newVideoURL) {
//             NewsHandler handler = new NewsHandler();
//             return handler.updateNewsURLs(postId, newImageURL, newVideoURL);
//         }
    


//         public boolean deletenews(String newsId) {
//             NewsHandler newsHandler = new NewsHandler();
//             return newsHandler.deleteNews(newsId);
//         }


     
//         /**
//          * @return
//          */
//         public List<News> getAllNews() {
//             var newsHandler = new NewsHandler();
//             return newsHandler.getAllNews();
//         }
        
//     }





     









package src.controllers;

import src.business.News;
import src.technical.NewsHandler;
import src.controllers.AuthController;

import javax.swing.*;
import java.util.List;

/**
 * News Controller with role-based access and search functionality
 */
public class NewsControllerView {

    private NewsHandler newsHandler;

    public NewsControllerView() {
        this.newsHandler = new NewsHandler();
    }

    /**
     * Add news (Admin only)
     */
    public boolean addNews(String title, String content, String category, String imageURL, String videoURL) {
        // Check if user is logged in and is admin
        if (!AuthController.isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only admins can post news!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (title == null || title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Title cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (content == null || content.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Content cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int authorId = AuthController.getCurrentUser().getUserId();
        News news = new News(title, content, category, imageURL, videoURL, authorId);
        return newsHandler.addNews(news);
    }

    /**
     * Edit news (Admin only)
     */
    public boolean editNews(int postId, String newTitle, String newContent, String newCategory, String newImageURL, String newVideoURL) {
        // Check if user is logged in and is admin
        if (!AuthController.isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only admins can edit news!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        News existingNews = newsHandler.getNewsById(postId);
        if (existingNews == null) {
            JOptionPane.showMessageDialog(null, "News not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Create updated news object
        News updatedNews = new News(postId, newTitle, newContent, newCategory, newImageURL, newVideoURL);
        updatedNews.setNewsId(postId);
        
        return newsHandler.updateNews(updatedNews);
    }

    /**
     * Delete news (Admin only)
     */
    public boolean deleteNews(String newsId) {
        // Check if user is logged in and is admin
        if (!AuthController.isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only admins can delete news!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int id = Integer.parseInt(newsId);
            return newsHandler.deleteNews(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid News ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Get all news
     */
    public List<News> getAllNews() {
        return newsHandler.getAllNews();
    }

    /**
     * Get all news with sorting
     */
    public List<News> getAllNews(String sortBy, String order) {
        return newsHandler.getAllNews(sortBy, order);
    }

    /**
     * Search news
     */
    public List<News> searchNews(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllNews();
        }
        return newsHandler.searchNews(searchTerm.trim());
    }

    /**
     * Get news by category
     */
    public List<News> getNewsByCategory(String category) {
        return newsHandler.getNewsByCategory(category);
    }

    /**
     * Get news by ID
     */
    public News getNewsById(int newsId) {
        return newsHandler.getNewsById(newsId);
    }
}
