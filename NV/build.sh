#!/bin/bash
# Build and Run script for NewsVine project

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}=== NewsVine Build Script ===${NC}"

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo -e "${RED}Error: Java compiler (javac) not found. Please install JDK.${NC}"
    exit 1
fi

# Check if MySQL connector exists
if [ ! -f "lib/mysql-connector-j-8.4.0.jar" ]; then
    echo -e "${RED}Error: MySQL connector JAR not found at lib/mysql-connector-j-8.4.0.jar${NC}"
    exit 1
fi

# Create bin directory if it doesn't exist
echo -e "${YELLOW}Creating bin directory...${NC}"
mkdir -p bin

# Clean previous build
echo -e "${YELLOW}Cleaning previous build...${NC}"
rm -rf bin/*

# Compile all Java files
echo -e "${YELLOW}Compiling Java files...${NC}"
javac -d bin -classpath lib/mysql-connector-j-8.4.0.jar:. \
    src/*.java \
    src/business/*.java \
    src/controllers/*.java \
    src/presentation/*.java \
    src/technical/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Compilation successful!${NC}"
    echo -e "${GREEN}Compiled classes are in bin/ directory${NC}"
else
    echo -e "${RED}✗ Compilation failed!${NC}"
    exit 1
fi

