#!/bin/bash
# Run script for NewsVine project

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}=== NewsVine Run Script ===${NC}"

# Check if bin directory exists
if [ ! -d "bin" ]; then
    echo -e "${RED}Error: bin directory not found. Please run build.sh first.${NC}"
    exit 1
fi

# Check if compiled classes exist
if [ ! -f "bin/src/Main.class" ]; then
    echo -e "${RED}Error: Main.class not found. Please run build.sh first.${NC}"
    exit 1
fi

# Check if MySQL connector exists
if [ ! -f "lib/mysql-connector-j-8.4.0.jar" ]; then
    echo -e "${RED}Error: MySQL connector JAR not found at lib/mysql-connector-j-8.4.0.jar${NC}"
    exit 1
fi

# Run the application
echo -e "${YELLOW}Starting NewsVine application...${NC}"
java -classpath bin:lib/mysql-connector-j-8.4.0.jar src.Main

# Check exit status
if [ $? -ne 0 ]; then
    echo -e "${RED}Application exited with an error.${NC}"
    echo -e "${YELLOW}Make sure:${NC}"
    echo -e "  1. MySQL server is running"
    echo -e "  2. Database 'news_vine' exists"
    echo -e "  3. Database credentials in DatabaseUtil.java are correct"
    exit 1
fi

