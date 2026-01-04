-- NewsVine Database Schema - Production Ready
-- Run this script to set up the database for the NewsVine application

-- Create database
CREATE DATABASE IF NOT EXISTS news_vine;
USE news_vine;

-- Drop tables if they exist (for clean setup - in correct order due to foreign keys)
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS users;

-- Create users table with role-based access control
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (user_email),
    INDEX idx_role (role)
);

-- Create news table with author support
CREATE TABLE news (
    news_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    news_category VARCHAR(100),
    imageURL VARCHAR(500),
    videoURL VARCHAR(500),
    author_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_category (news_category),
    INDEX idx_created_at (created_at),
    INDEX idx_author (author_id)
);

-- Create comments table
CREATE TABLE comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    news_id INT NOT NULL,
    user_id INT NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (news_id) REFERENCES news(news_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_news_id (news_id),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
);

-- Insert default admin user (password: admin123 - should be changed in production)
-- Password hash for 'admin123' using SHA-256 (will implement proper hashing in Java)
INSERT INTO users (user_name, user_email, password_hash, role) VALUES
('Admin User', 'admin@newsvine.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN');

-- Insert sample regular user (password: user123)
INSERT INTO users (user_name, user_email, password_hash, role) VALUES
('Test User', 'user@newsvine.com', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 'USER');

-- Insert sample news (after users are created)
INSERT INTO news (title, content, news_category, imageURL, videoURL, author_id) VALUES
('Welcome to NewsVine', 'This is a sample news article to get you started. NewsVine is a professional news management system with role-based access control.', 'General', 'https://example.com/image1.jpg', 'https://example.com/video1.mp4', 1),
('Technology Update', 'Latest technology news and updates from the tech world. Stay informed about the latest innovations and breakthroughs.', 'Technology', 'https://example.com/image2.jpg', NULL, 1),
('Sports Highlights', 'Today\'s sports highlights and scores from around the world. Get the latest updates on your favorite teams.', 'Sports', NULL, 'https://example.com/video2.mp4', 1);

-- Display success message
SELECT 'Database setup completed successfully!' AS Status;
SELECT 'Default Admin: admin@newsvine.com / admin123' AS AdminCredentials;
SELECT 'Default User: user@newsvine.com / user123' AS UserCredentials;

-- Display success message
SELECT 'Database setup completed successfully!' AS Status;

