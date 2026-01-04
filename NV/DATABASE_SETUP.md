# Database Setup Guide for NewsVine

This guide will help you set up the MySQL database for the NewsVine application.

## Prerequisites

1. **MySQL Server** must be installed and running
   - Check if MySQL is installed: `mysql --version`
   - On macOS: `brew services start mysql` or use MySQL Workbench
   - On Linux: `sudo systemctl start mysql`
   - On Windows: Start MySQL from Services or MySQL Workbench

2. **MySQL root access** or a user with database creation privileges

## Method 1: Command Line (Recommended)

### Step 1: Navigate to Project Directory
```bash
cd "/Users/shera/Downloads/All Data/5th_sem/SC/NewsVine/News-Vine/NV"
```

### Step 2: Create Database and Run Setup Script

**Option A: Using MySQL command line (if password is empty)**
```bash
mysql -u root < setup_database.sql
```

**Option B: Using MySQL command line (with password prompt)**
```bash
mysql -u root -p < setup_database.sql
```
*You'll be prompted to enter your MySQL root password*

**Option C: Create database first, then run script**
```bash
# Step 1: Create database
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS news_vine;"

# Step 2: Run setup script
mysql -u root -p news_vine < setup_database.sql
```

### Step 3: Verify Database Setup

Connect to MySQL and verify:
```bash
mysql -u root -p
```

Then run these commands in MySQL:
```sql
USE news_vine;
SHOW TABLES;
SELECT * FROM users;
SELECT * FROM news;
```

You should see:
- 3 tables: `users`, `news`, `comments`
- 2 users: admin and test user
- 3 sample news articles

## Method 2: MySQL Workbench (GUI Method)

### Step 1: Open MySQL Workbench

1. Launch MySQL Workbench
2. Connect to your MySQL server (usually `localhost:3306`)

### Step 2: Create Database

1. Click on the "Server Administration" tab or use SQL Editor
2. Run this command:
```sql
CREATE DATABASE IF NOT EXISTS news_vine;
```

### Step 3: Select Database

1. Click on "Schemas" in the left panel
2. Right-click on `news_vine` → "Set as Default Schema"
   OR
   Run: `USE news_vine;`

### Step 4: Run Setup Script

1. Go to File → Open SQL Script
2. Navigate to: `News-Vine/NV/setup_database.sql`
3. Open the file
4. Click the "Execute" button (⚡ icon) or press `Ctrl+Shift+Enter` (Windows/Linux) or `Cmd+Shift+Enter` (Mac)

### Step 5: Verify Setup

Run these queries to verify:
```sql
SHOW TABLES;
SELECT * FROM users;
SELECT * FROM news;
```

## Method 3: Manual Setup (Step by Step)

If you prefer to run commands manually:

### Step 1: Connect to MySQL
```bash
mysql -u root -p
```

### Step 2: Create Database
```sql
CREATE DATABASE IF NOT EXISTS news_vine;
USE news_vine;
```

### Step 3: Create Tables

Copy and paste the table creation statements from `setup_database.sql`:

```sql
-- Create users table
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

-- Create news table
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
```

### Step 4: Insert Default Data

```sql
-- Insert admin user (password: admin123)
INSERT INTO users (user_name, user_email, password_hash, role) VALUES
('Admin User', 'admin@newsvine.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN');

-- Insert test user (password: user123)
INSERT INTO users (user_name, user_email, password_hash, role) VALUES
('Test User', 'user@newsvine.com', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 'USER');

-- Insert sample news
INSERT INTO news (title, content, news_category, imageURL, videoURL, author_id) VALUES
('Welcome to NewsVine', 'This is a sample news article to get you started. NewsVine is a professional news management system with role-based access control.', 'General', 'https://example.com/image1.jpg', 'https://example.com/video1.mp4', 1),
('Technology Update', 'Latest technology news and updates from the tech world. Stay informed about the latest innovations and breakthroughs.', 'Technology', 'https://example.com/image2.jpg', NULL, 1),
('Sports Highlights', 'Today\'s sports highlights and scores from around the world. Get the latest updates on your favorite teams.', 'Sports', NULL, 'https://example.com/video2.mp4', 1);
```

## Verify Database Connection

After setup, test the connection:

### Option 1: Using TestConnection.java
```bash
cd News-Vine/NV
javac -d bin -classpath lib/mysql-connector-j-8.4.0.jar:. src/business/TestConnection.java
java -classpath bin:lib/mysql-connector-j-8.4.0.jar src.business.TestConnection
```

### Option 2: Check DatabaseUtil.java Configuration

Make sure the database credentials in `src/technical/DatabaseUtil.java` match your MySQL setup:

```java
private static final String URL = "jdbc:mysql://localhost:3306/news_vine";
private static final String USER = "root";  // Change if needed
private static final String PASSWORD = "";   // Change if you have a password
```

## Default Login Credentials

After setup, you can login with:

1. **Admin Account**
   - Email: `admin@newsvine.com`
   - Password: `admin123`
   - Role: ADMIN

2. **User Account**
   - Email: `user@newsvine.com`
   - Password: `user123`
   - Role: USER

## Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"
- Make sure MySQL is running
- Check your MySQL root password
- Try: `mysql -u root -p` and enter password when prompted

### Error: "Unknown database 'news_vine'"
- Create the database first: `CREATE DATABASE news_vine;`
- Or uncomment the CREATE DATABASE line in setup_database.sql

### Error: "Table already exists"
- The script will drop existing tables, but if you get this error:
- Manually drop tables: `DROP TABLE IF EXISTS comments, news, users;`
- Then run the setup script again

### Error: "Can't connect to MySQL server"
- Make sure MySQL server is running
- Check if MySQL is listening on port 3306
- Verify connection settings in DatabaseUtil.java

### Connection Issues in Application
- Verify database name is `news_vine`
- Check username and password in `DatabaseUtil.java`
- Ensure MySQL server is running
- Test connection using TestConnection.java

## Quick Setup Script

For convenience, here's a one-liner (adjust password as needed):

```bash
cd "/Users/shera/Downloads/All Data/5th_sem/SC/NewsVine/News-Vine/NV" && \
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS news_vine;" && \
mysql -u root -p news_vine < setup_database.sql
```

## Next Steps

After database setup:
1. Build the project: `./build.sh`
2. Run the application: `./run.sh`
3. Login with default credentials
4. Start using NewsVine!

---

**Need Help?** Check `README_SETUP.md` for more detailed setup instructions.

