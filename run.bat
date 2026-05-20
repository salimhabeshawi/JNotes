@echo off
REM Run the modular Swing Notepad application on Windows

REM Check if compiled classes exist
if not exist out\classes (
    echo Classes not found. Building project...
    call build.bat
    if errorlevel 1 (
        echo Build failed!
        exit /b 1
    )
)

REM Run the application
java -cp out\classes com.notepad.NotepadApplication
