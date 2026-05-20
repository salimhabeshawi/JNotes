#!/bin/bash

# Build script for modular Swing Notepad application

echo "Building Modular Swing Notepad Application..."
echo "=============================================="

# Create output directory
mkdir -p out/classes

# Compile all Java files
echo "Compiling source files..."
javac -d out/classes -sourcepath src src/com/notepad/NotepadApplication.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
else
    echo "✗ Compilation failed!"
    exit 1
fi

echo ""
echo "Build complete! Output: out/classes/"
echo ""
echo "To run the application, use: ./run.sh"
