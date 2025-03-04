#!/bin/bash

# Install Java (using openjdk-17 as an example, feel free to change version)
echo "Installing Java 17..."
sudo apt update
sudo apt install -y openjdk-17-jdk

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Check Java version to ensure it's installed correctly
java -version

# Install Maven
echo "Installing Maven..."
sudo apt install -y maven

# Check Maven version
mvn -version

# Give executable permissions to mvnw
chmod +x mvnw

# Run the Maven build
./mvnw clean install
