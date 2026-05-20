#!/bin/bash

# Run the modular Swing Notepad application

cd "$(dirname "$0")"

# Check if compiled classes exist
if [ ! -d "out/classes" ]; then
    echo "Classes not found. Building project..."
    bash build.sh
    if [ $? -ne 0 ]; then
        echo "Build failed!"
        exit 1
    fi
fi

# Run the application
java -cp out/classes com.notepad.NotepadApplication
