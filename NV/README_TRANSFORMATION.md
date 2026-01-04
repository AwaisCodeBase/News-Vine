# NewsVine - Professional Transformation Complete! 🎉

## Overview

NewsVine has been successfully transformed into a **production-ready** news management system with role-based access control, secure authentication, and a modern user interface.

## ✨ Key Features Implemented

### 🔐 Authentication & Security
- **Secure Login/Registration** with password hashing (SHA-256)
- **Role-Based Access Control (RBAC)** - Admin and User roles
- **Session Management** - Tracks current logged-in user
- **Input Validation** - Prevents SQL injection and validates user input
- **Prepared Statements** - All database queries use prepared statements

### 👨‍💼 Admin Features
- **Full CRUD Operations** on news articles
- **Post News** - Create new articles with title, content, category, images, videos
- **Edit News** - Update existing articles
- **Delete News** - Remove articles with confirmation
- **View All News** - See all published articles
- **Comment Moderation** - Delete inappropriate comments

### 👤 User Features
- **View News** - Browse all published articles
- **Search News** - Search by title, content, or category
- **Add Comments** - Comment on news articles
- **Read Comments** - View comments from other users
- **Clean Interface** - User-friendly news feed

### 🗄️ Database Enhancements
- **Users Table** with roles (ADMIN/USER)
- **News Table** with author tracking and timestamps
- **Comments Table** with user association
- **Indexes** for better query performance
- **Foreign Keys** for data integrity

### 🎨 UI/UX Improvements
- **Modern Design** - Clean, professional interface
- **Color-Coded Roles** - Blue for Admin, Green for User
- **Card-Based Layout** - News displayed in attractive cards
- **Responsive Layouts** - Proper spacing and alignment
- **User Feedback** - Clear success/error messages

## 📁 Project Structure

```
NV/
├── src/
│   ├── Main.java                    # Entry point - starts with LoginFrame
│   ├── business/                    # Business Logic Layer
│   │   ├── User.java               # User entity with roles
│   │   ├── News.java               # News entity with author support
│   │   └── Comment.java            # Comment entity
│   ├── controllers/                 # Controller Layer (MVC)
│   │   ├── AuthController.java     # Authentication & authorization
│   │   ├── NewsControllerView.java   # News operations with RBAC
│   │   └── CommentController.java  # Comment operations
│   ├── presentation/                # Presentation Layer (GUI)
│   │   ├── LoginFrame.java         # Login/Registration screen
│   │   ├── AdminDashboard.java     # Admin control panel
│   │   ├── UserDashboard.java      # User dashboard
│   │   ├── PostingNewsScreen.java  # Post news (Admin)
│   │   ├── DisplayNewsScreen.java  # View news (Admin)
│   │   ├── EditNewsScreen.java     # Edit news (Admin)
│   │   ├── DeleteNewsScreen.java   # Delete news (Admin)
│   │   ├── UserNewsViewScreen.java # View news with comments (User)
│   │   └── SearchNewsScreen.java   # Search news (User)
│   └── technical/                  # Technical/Data Access Layer
│       ├── DatabaseUtil.java       # Database connection
│       ├── PasswordUtil.java        # Password hashing utility
│       ├── UserHandler.java         # User database operations
│       ├── NewsHandler.java         # News database operations
│       └── CommentHandler.java     # Comment database operations
├── lib/
│   └── mysql-connector-j-8.4.0.jar
├── setup_database.sql              # Updated database schema
└── README_SETUP.md                 # Setup instructions
```

## 🚀 Quick Start

### 1. Database Setup
```bash
mysql -u root -p < setup_database.sql
```

**Default Credentials:**
- **Admin**: `admin@newsvine.com` / `admin123`
- **User**: `user@newsvine.com` / `user123`

### 2. Build & Run
```bash
cd News-Vine/NV
./build.sh
./run.sh
```

## 🔑 Default Accounts

After running `setup_database.sql`, you can login with:

1. **Admin Account**
   - Email: `admin@newsvine.com`
   - Password: `admin123`
   - Role: ADMIN (full access)

2. **User Account**
   - Email: `user@newsvine.com`
   - Password: `user123`
   - Role: USER (view and comment only)

## 🛡️ Security Features

1. **Password Hashing**: All passwords are hashed using SHA-256
2. **Prepared Statements**: Prevents SQL injection attacks
3. **Role Validation**: Every admin action checks user role
4. **Input Validation**: All user inputs are validated
5. **Session Management**: Secure user session tracking

## 📊 Database Schema

### Users Table
- `user_id` (Primary Key, Auto Increment)
- `user_name` (VARCHAR)
- `user_email` (UNIQUE, VARCHAR)
- `password_hash` (VARCHAR) - Hashed password
- `role` (ENUM: 'ADMIN', 'USER')
- `created_at` (TIMESTAMP)

### News Table
- `news_id` (Primary Key, Auto Increment)
- `title` (VARCHAR)
- `content` (TEXT)
- `news_category` (VARCHAR)
- `imageURL` (VARCHAR)
- `videoURL` (VARCHAR)
- `author_id` (Foreign Key → users.user_id)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Comments Table
- `comment_id` (Primary Key, Auto Increment)
- `news_id` (Foreign Key → news.news_id)
- `user_id` (Foreign Key → users.user_id)
- `comment` (TEXT)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## 🎯 Architecture Pattern

The application follows **MVC (Model-View-Controller)** pattern:

- **Model**: Business entities (User, News, Comment)
- **View**: Presentation layer (Swing GUI screens)
- **Controller**: Business logic and data access coordination

## 📝 Notes

- All database operations use **Prepared Statements** for security
- Passwords are **hashed** before storage
- **Role-based access** is enforced at controller level
- UI uses **System Look and Feel** for native appearance
- Error handling with user-friendly messages

## 🔄 Migration from Old System

The old in-memory user storage has been replaced with:
- Database-backed user management
- Persistent news storage with author tracking
- Comment system with user association
- Role-based access control

## 🎓 Production Readiness

This application is now:
- ✅ **Secure** - Password hashing, SQL injection prevention
- ✅ **Scalable** - Proper database design with indexes
- ✅ **Maintainable** - Clean MVC architecture
- ✅ **User-Friendly** - Modern, intuitive interface
- ✅ **Professional** - Ready for academic/resume presentation

---

**Built with ❤️ for authentic news management**

