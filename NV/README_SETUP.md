# NewsVine Setup Guide

## Prerequisites

1. **Java JDK** (8 or higher)
   - Check installation: `java -version` and `javac -version`
   - Download from: https://www.oracle.com/java/technologies/downloads/

2. **MySQL Server**
   - Check installation: `mysql --version`
   - Download from: https://dev.mysql.com/downloads/mysql/

## Setup Instructions

### Step 1: Database Setup

1. Start MySQL server:
   ```bash
   # On macOS/Linux:
   sudo systemctl start mysql
   # OR
   mysql.server start
   
   # On Windows:
   # Start MySQL from Services or use MySQL Workbench
   ```

2. Create the database and tables:
   ```bash
   mysql -u root -p < setup_database.sql
   ```
   
   Or manually:
   ```bash
   mysql -u root -p
   ```
   Then in MySQL prompt:
   ```sql
   CREATE DATABASE IF NOT EXISTS news_vine;
   USE news_vine;
   SOURCE setup_database.sql;
   ```

3. Verify database connection:
   - Update credentials in `src/technical/DatabaseUtil.java` if needed
   - Default: user=`root`, password=empty
   - Test connection: `java -cp bin:lib/mysql-connector-j-8.4.0.jar src.business.TestConnection`

### Step 2: Build the Project

1. Navigate to the project directory:
   ```bash
   cd News-Vine/NV
   ```

2. Make scripts executable:
   ```bash
   chmod +x build.sh run.sh compile.sh
   ```

3. Build the project:
   ```bash
   ./build.sh
   ```
   
   Or manually:
   ```bash
   ./compile.sh
   ```

### Step 3: Run the Application

```bash
./run.sh
```

Or manually:
```bash
java -classpath bin:lib/mysql-connector-j-8.4.0.jar src.Main
```

## Project Structure

```
NV/
├── src/                    # Source code
│   ├── Main.java          # Entry point
│   ├── business/          # Business logic
│   ├── controllers/       # Controllers
│   ├── presentation/      # GUI (Swing)
│   └── technical/         # Database utilities
├── lib/                   # External libraries
│   └── mysql-connector-j-8.4.0.jar
├── bin/                   # Compiled classes (created after build)
├── build.sh               # Build script
├── run.sh                 # Run script
├── compile.sh             # Simple compile script
└── setup_database.sql     # Database schema
```

## Troubleshooting

### Compilation Errors
- Ensure Java JDK is installed: `javac -version`
- Check that all source files are present
- Verify MySQL connector JAR exists

### Database Connection Errors
- Verify MySQL server is running
- Check database name is `news_vine`
- Update credentials in `DatabaseUtil.java`
- Test connection using `TestConnection.java`

### Runtime Errors
- Ensure project is built first (`./build.sh`)
- Check classpath includes both `bin` and `lib/mysql-connector-j-8.4.0.jar`
- Verify database tables exist

## Features

- **Post News**: Create new news articles
- **Edit News**: Update existing news articles
- **Delete News**: Remove news articles
- **Display News**: View all news articles

## Database Schema

### news table
- `news_id` (INT, PRIMARY KEY)
- `title` (VARCHAR)
- `content` (TEXT)
- `news_category` (VARCHAR)
- `imageURL` (VARCHAR)
- `videoURL` (VARCHAR)
- `rating` (INT)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## Notes

- The application uses Swing for the GUI
- Database connection is configured in `src/technical/DatabaseUtil.java`
- Sample data is included in `setup_database.sql`

