#!/bin/bash
# Install tput (from ncurses-utils) on Render
apt-get update
apt-get install -y ncurses-utils

# Run the maven build
./mvnw clean install