#!/bin/bash
# Compile script for NewsVine project

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files with proper classpath
javac -d bin -classpath lib/mysql-connector-j-8.4.0.jar:. \
    src/*.java \
    src/business/*.java \
    src/controllers/*.java \
    src/presentation/*.java \
    src/technical/*.java

echo "Compilation complete! Check bin/ directory for compiled classes."