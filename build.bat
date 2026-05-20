@echo off
REM Build script for modular Swing Notepad application on Windows

echo Building Modular Swing Notepad Application...
echo =============================================

REM Create output directory
if not exist out\classes mkdir out\classes

REM Compile all Java files
echo Compiling source files...
javac -d out\classes -sourcepath src src\com\notepad\NotepadApplication.java

if errorlevel 1 (
    echo Compilation failed!
    exit /b 1
) else (
    echo Compilation successful!
)

echo.
echo Build complete! Output: out\classes\
echo.
echo To run the application, use: run.bat
