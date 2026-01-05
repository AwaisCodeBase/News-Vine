# NewsVine - Authentic News Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![Swing](https://img.shields.io/badge/Java%20Swing-GUI-green.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

**A professional desktop application for managing and viewing news articles with role-based access control**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Project Structure](#-project-structure)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Running the Application](#-running-the-application)
- [Project Structure](#-project-structure)
- [Usage Guide](#-usage-guide)
- [Screenshots](#-screenshots)
- [Troubleshooting](#-troubleshooting)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

NewsVine is a comprehensive desktop news management system built with Java Swing and MySQL. It provides a complete solution for publishing, managing, and viewing news articles with support for images, videos, comments, and role-based access control.

### Key Highlights

- **Role-Based Access Control**: Separate interfaces for Administrators and Regular Users
- **Rich Media Support**: Display images from URLs (Unsplash, direct links) and YouTube video thumbnails
- **Interactive Comments**: Users can post and view comments on news articles
- **Modern UI/UX**: Professional, responsive design with intuitive navigation
- **Secure Authentication**: Password hashing using SHA-256 algorithm
- **Database-Driven**: MySQL backend for persistent data storage

---

## ✨ Features

### For Administrators

- ✅ **Post News**: Create new news articles with title, content, category, images, and videos
- ✅ **View News**: Browse all published news articles with rich media display
- ✅ **Edit News**: Update existing news articles
- ✅ **Delete News**: Remove news articles with confirmation dialogs
- ✅ **Manage Comments**: View and moderate user comments

### For Regular Users

- ✅ **View News**: Browse all published news articles
- ✅ **Search News**: Search articles by title, content, or category
- ✅ **Filter by Category**: Filter news by categories (General, Technology, Sports, Politics, Entertainment)
- ✅ **Post Comments**: Add comments to news articles
- ✅ **View Comments**: See all comments with timestamps and author names

### Technical Features

- 🔐 **Secure Authentication**: SHA-256 password hashing
- 🖼️ **Image Loading**: Support for Unsplash URLs, direct image URLs, and YouTube thumbnails
- 🎨 **Modern UI**: Professional color scheme with proper contrast and visibility
- 📱 **Responsive Design**: Adapts to different screen sizes
- 🔄 **Auto-Refresh**: Refresh news lists after updates
- ⚠️ **Error Handling**: Comprehensive error handling with user-friendly messages
- 🗄️ **Database Integration**: MySQL for data persistence

---

## 🛠️ Technology Stack

- **Programming Language**: Java 17+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **Build Tool**: Shell Scripts (compile.sh, build.sh, run.sh)
- **Password Hashing**: SHA-256
- **Image Processing**: Java ImageIO, BufferedImage

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher
  ```bash
  java -version
  ```

- **MySQL Server**: Version 8.0 or higher
  ```bash
  mysql --version
  ```

- **MySQL Connector/J**: JDBC driver for MySQL (included in project)
  - File: `mysql-connector-java-8.0.33.jar` (or compatible version)

- **Terminal/Command Line**: For running build scripts

---

## 🚀 Installation

### 1. Clone or Download the Project

```bash
cd /path/to/your/projects
# If using git:
git clone <repository-url>
# Or extract the downloaded ZIP file
```

### 2. Navigate to Project Directory

```bash
cd NewsVine/News-Vine/NV
```

### 3. Verify Project Structure

Ensure you have the following structure:
```
NV/
├── src/
│   ├── Main.java
│   ├── business/
│   ├── controllers/
│   ├── presentation/
│   └── technical/
├── bin/
├── lib/
│   └── mysql-connector-java-8.0.33.jar
├── compile.sh
├── build.sh
├── run.sh
├── setup_database.sql
└── README.md
```

---

## 🗄️ Database Setup

### Step 1: Start MySQL Server

```bash
# On macOS/Linux:
sudo systemctl start mysql
# Or:
brew services start mysql

# On Windows:
# Start MySQL from Services or use MySQL Workbench
```

### Step 2: Create Database and Tables

```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source /path/to/NewsVine/News-Vine/NV/setup_database.sql

# Or copy and paste the SQL commands from setup_database.sql
```

### Step 3: Verify Database Configuration

Edit `src/technical/DatabaseUtil.java` if needed:

```java
private static final String URL = "jdbc:mysql://localhost:3306/news_vine";
private static final String USER = "root";        // Your MySQL username
private static final String PASSWORD = "";        // Your MySQL password
```

### Step 4: Verify Database Connection

The database should contain:
- `users` table (with sample admin/user accounts)
- `news` table (for storing news articles)
- `comments` table (for storing user comments)

**Default Admin Account:**
- Email: `admin@newsvine.com`
- Password: `admin123`
- Role: `ADMIN`

**Default User Account:**
- Email: `user@newsvine.com`
- Password: `user123`
- Role: `USER`

---

## ▶️ Running the Application

### Method 1: Using Build Scripts (Recommended)

```bash
# Make scripts executable (first time only)
chmod +x build.sh run.sh compile.sh

# Build the project
./build.sh

# Run the application
./run.sh
```

### Method 2: Manual Compilation and Execution

```bash
# Compile
javac -cp "lib/mysql-connector-java-8.0.33.jar:bin" -d bin src/**/*.java

# Run
java -cp "bin:lib/mysql-connector-java-8.0.33.jar" src.Main
```

### Method 3: Using IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, NetBeans)
2. Add `lib/mysql-connector-java-8.0.33.jar` to the classpath
3. Run `src/Main.java`

---

## 📁 Project Structure

```
NewsVine/
└── News-Vine/
    └── NV/
        ├── src/
        │   ├── Main.java                    # Application entry point
        │   │
        │   ├── business/                    # Business logic / Entity classes
        │   │   ├── News.java                # News entity
        │   │   ├── User.java                # User entity with roles
        │   │   └── Comment.java             # Comment entity
        │   │
        │   ├── controllers/                 # Controllers (MVC pattern)
        │   │   ├── AuthController.java      # Authentication & authorization
        │   │   ├── NewsControllerView.java   # News operations
        │   │   └── CommentController.java   # Comment operations
        │   │
        │   ├── presentation/                # UI components (Swing)
        │   │   ├── LoginFrame.java          # Login/Registration screen
        │   │   ├── AdminDashboard.java      # Admin main screen
        │   │   ├── UserDashboard.java       # User main screen
        │   │   ├── PostingNewsScreen.java   # Post news screen
        │   │   ├── DisplayNewsScreen.java  # View news screen (Admin)
        │   │   ├── UserNewsViewScreen.java  # View news screen (User)
        │   │   ├── EditNewsScreen.java      # Edit news screen
        │   │   ├── DeleteNewsScreen.java    # Delete news screen
        │   │   └── SearchNewsScreen.java    # Search news screen
        │   │
        │   └── technical/                  # Technical layer / Utilities
        │       ├── DatabaseUtil.java        # Database connection
        │       ├── UserHandler.java         # User database operations
        │       ├── NewsHandler.java         # News database operations
        │       ├── CommentHandler.java      # Comment database operations
        │       ├── PasswordUtil.java        # Password hashing (SHA-256)
        │       └── ImageUtil.java           # Image loading from URLs
        │
        ├── lib/                              # External libraries
        │   └── mysql-connector-java-8.0.33.jar
        │
        ├── bin/                              # Compiled classes (generated)
        │
        ├── compile.sh                        # Compilation script
        ├── build.sh                          # Build script
        ├── run.sh                            # Run script
        ├── setup_database.sql                # Database setup script
        ├── README.md                          # This file
        └── DATABASE_SETUP.md                 # Detailed database setup guide
```

---

## 📖 Usage Guide

### For Administrators

1. **Login**
   - Use admin credentials (default: `admin@newsvine.com` / `admin123`)
   - Or register a new admin account

2. **Post News**
   - Click "📰 Post News" button
   - Fill in title (required), content (required), category, image URL, video URL
   - Click "Post News" to publish

3. **View News**
   - Click "👁️ View News" button
   - Browse all published articles
   - Use "🔄 Refresh" to reload the list
   - Click "← Back" to return to dashboard

4. **Edit News**
   - From "View News" screen, click "✏️ Edit" on any article
   - Modify fields and click "💾 Save Changes"

5. **Delete News**
   - From "View News" screen, click "🗑️ Delete" on any article
   - Confirm deletion in the dialog

### For Regular Users

1. **Login/Register**
   - Login with user credentials or register a new account
   - Default user: `user@newsvine.com` / `user123`

2. **View News**
   - Click "View News" to browse all articles
   - Use category filter dropdown to filter by category
   - Images and videos are displayed automatically

3. **Search News**
   - Click "Search News" button
   - Enter search term (searches title, content, category)
   - Click "🔍 Search" to find matching articles

4. **Post Comments**
   - While viewing news, scroll to comments section
   - Type your comment in the text field
   - Click "💬 Post Comment" to submit

### Image URL Formats Supported

- **Unsplash**: `https://unsplash.com/photos/[photo-id]`
- **Direct Image URLs**: `https://example.com/image.jpg`
- **YouTube Videos**: `https://www.youtube.com/watch?v=[video-id]` (displays thumbnail)

---

## 🖼️ Screenshots

### Login Screen
- Clean, professional login interface
- Toggle between Login and Registration
- Role selection during registration

### Admin Dashboard
- Four main action buttons: Post, View, Edit, Delete
- Welcome message with user name
- Logout functionality

### News View
- Card-based layout for news articles
- Rich media display (images/videos)
- Edit and Delete buttons for admins
- Comments section for users

---

## 🔧 Troubleshooting

### Common Issues

**1. "Database connection failed"**
- ✅ Ensure MySQL server is running
- ✅ Verify database credentials in `DatabaseUtil.java`
- ✅ Check if `news_vine` database exists
- ✅ Verify MySQL connector JAR is in `lib/` folder

**2. "ClassNotFoundException: com.mysql.cj.jdbc.Driver"**
- ✅ Ensure `mysql-connector-java-8.0.33.jar` is in `lib/` folder
- ✅ Check classpath includes the JAR file
- ✅ Rebuild the project: `./build.sh`

**3. "Images not displaying"**
- ✅ Check internet connection (for external URLs)
- ✅ Verify image URL is valid and accessible
- ✅ Check console for error messages
- ✅ Try a direct image URL first to test

**4. "Login button not visible"**
- ✅ This has been fixed in recent updates
- ✅ Buttons now have white background with blue text
- ✅ Rebuild the project if issue persists

**5. "Edit News crashes"**
- ✅ Ensure news object is not null
- ✅ Check database connection
- ✅ Verify news ID exists in database

### Debug Mode

Enable console logging by checking:
- Console output for image loading messages
- Database connection errors
- Authentication errors

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java naming conventions
- Add comments for complex logic
- Test all features before committing
- Update documentation for new features

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👥 Authors

- **Development Team** - *Initial work* - NewsVine

---

## 🙏 Acknowledgments

- Java Swing community for UI components
- MySQL for database support
- Unsplash for image hosting
- YouTube for video thumbnail API

---

## 📞 Support

For issues, questions, or contributions:
- Open an issue on the repository
- Check the troubleshooting section
- Review the `DATABASE_SETUP.md` for database-specific help

---

<div align="center">

**Made with ❤️ using Java Swing and MySQL**

⭐ Star this repo if you find it helpful!

</div>

