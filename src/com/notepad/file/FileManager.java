package com.notepad.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles all file operations for the Notepad application.
 * Manages reading, writing, and file path tracking.
 */
public class FileManager {
    private String currentFilePath;
    private boolean modified;

    public FileManager() {
        this.currentFilePath = null;
        this.modified = false;
    }

    /**
     * Reads content from a file.
     *
     * @param filePath Path to the file to read
     * @return File content as string
     * @throws IOException If file cannot be read
     */
    public String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Writes content to a file.
     *
     * @param filePath Path to the file to write to
     * @param content  Content to write
     * @throws IOException If file cannot be written
     */
    public void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }

    /**
     * Gets the current file name (without path).
     *
     * @return File name or "Untitled" if no file is open
     */
    public String getFileName() {
        if (currentFilePath != null) {
            return new File(currentFilePath).getName();
        }
        return "Untitled";
    }

    /**
     * Gets the full path of the current file.
     *
     * @return Current file path or null if no file is open
     */
    public String getCurrentFilePath() {
        return currentFilePath;
    }

    /**
     * Sets the current file path.
     *
     * @param filePath Path to set as current
     */
    public void setCurrentFilePath(String filePath) {
        this.currentFilePath = filePath;
    }

    /**
     * Clears the current file path.
     */
    public void clearCurrentFilePath() {
        this.currentFilePath = null;
    }

    /**
     * Checks if the current document has unsaved changes.
     *
     * @return true if modified, false otherwise
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Sets the modified state.
     *
     * @param modified true if modified, false otherwise
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * Checks if a file is currently open.
     *
     * @return true if a file is open, false otherwise
     */
    public boolean isFileOpen() {
        return currentFilePath != null;
    }
}
