package com.notepad;

import com.notepad.ui.NotepadFrame;

import javax.swing.*;

/**
 * NotepadApplication is the entry point for the modular Swing-based Notepad application.
 * Initializes the application on the Event Dispatch Thread with professional GUI framework setup.
 */
public class NotepadApplication {

    /**
     * Main method to launch the Notepad application.
     * Ensures the GUI is created on the Event Dispatch Thread.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Schedule GUI creation on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to set look and feel: " + e.getMessage());
            }

            // Create and display the main window
            new NotepadFrame();
        });
    }
}


