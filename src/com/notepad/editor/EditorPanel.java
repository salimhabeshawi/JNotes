package com.notepad.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * EditorPanel provides the text editing component for the Notepad.
 * Handles text rendering, formatting, and user input.
 */
public class EditorPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Runnable onModified;

    public EditorPanel() {
        setLayout(new BorderLayout());
        initializeTextArea();
    }

    /**
     * Initializes the text area with default settings.
     */
    private void initializeTextArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        
        // Add key listener to detect modifications
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (onModified != null) {
                    onModified.run();
                }
            }
        });

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Gets the underlying JTextArea component.
     *
     * @return JTextArea instance
     */
    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * Sets the content of the editor.
     *
     * @param text Text to set
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    /**
     * Gets the current content of the editor.
     *
     * @return Current text
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * Clears the editor content.
     */
    public void clear() {
        textArea.setText("");
    }

    /**
     * Sets callback for when text is modified.
     *
     * @param callback Runnable to execute on modification
     */
    public void setOnModified(Runnable callback) {
        this.onModified = callback;
    }

    /**
     * Toggles line wrapping.
     *
     * @param enabled true to enable, false to disable
     */
    public void setLineWrap(boolean enabled) {
        textArea.setLineWrap(enabled);
    }

    /**
     * Gets the current line wrap state.
     *
     * @return true if line wrap is enabled
     */
    public boolean isLineWrap() {
        return textArea.getLineWrap();
    }

    /**
     * Requests focus for the text area.
     */
    public void requestTextAreaFocus() {
        textArea.requestFocusInWindow();
    }
}
