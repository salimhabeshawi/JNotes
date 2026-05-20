package com.notepad.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Document model representing the notepad document state.
 * Manages document content, file path, and modification status.
 */
public class Document {
    private final SimpleStringProperty content = new SimpleStringProperty("");
    private final SimpleStringProperty filePath = new SimpleStringProperty(null);
    private final SimpleBooleanProperty modified = new SimpleBooleanProperty(false);
    private final SimpleStringProperty fileName = new SimpleStringProperty("Untitled");

    public Document() {
        // Listen to content changes to update modified status
        content.addListener((obs, oldVal, newVal) -> {
            if (!modified.get()) {
                modified.set(true);
            }
        });
    }

    // Content properties
    public String getContent() {
        return content.get();
    }

    public void setContent(String value) {
        content.set(value);
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    // File path properties
    public String getFilePath() {
        return filePath.get();
    }

    public void setFilePath(String value) {
        filePath.set(value);
    }

    public SimpleStringProperty filePathProperty() {
        return filePath;
    }

    // File name properties
    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String value) {
        fileName.set(value);
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    // Modified status properties
    public boolean isModified() {
        return modified.get();
    }

    public void setModified(boolean value) {
        modified.set(value);
    }

    public SimpleBooleanProperty modifiedProperty() {
        return modified;
    }

    /**
     * Resets the document to a clean state.
     */
    public void reset() {
        content.set("");
        filePath.set(null);
        fileName.set("Untitled");
        modified.set(false);
    }

    /**
     * Marks the document as saved.
     */
    public void markSaved() {
        modified.set(false);
    }

    /**
     * Checks if a file is currently open.
     */
    public boolean isFileOpen() {
        return filePath.get() != null;
    }
}
