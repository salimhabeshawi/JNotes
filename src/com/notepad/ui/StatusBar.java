package com.notepad.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * StatusBar displays status messages and information about the current document.
 * Located at the bottom of the application window.
 */
public class StatusBar extends JLabel {

    public StatusBar() {
        super("Ready");
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setHorizontalAlignment(JLabel.LEFT);
    }

    /**
     * Updates the status message.
     *
     * @param message Message to display
     */
    public void setStatus(String message) {
        setText(message);
    }

    /**
     * Resets the status to "Ready".
     */
    public void reset() {
        setText("Ready");
    }

    /**
     * Shows a file opened message.
     *
     * @param fileName Name of the opened file
     */
    public void fileOpened(String fileName) {
        setText("File opened: " + fileName);
    }

    /**
     * Shows a file saved message.
     *
     * @param fileName Name of the saved file
     */
    public void fileSaved(String fileName) {
        setText("File saved: " + fileName);
    }

    /**
     * Shows an error message.
     *
     * @param errorMessage Error message to display
     */
    public void showError(String errorMessage) {
        setText("Error: " + errorMessage);
    }
}
