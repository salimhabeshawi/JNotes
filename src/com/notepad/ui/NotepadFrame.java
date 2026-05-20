package com.notepad.ui;

import com.notepad.editor.EditorPanel;
import com.notepad.file.FileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * NotepadFrame is the main window for the Notepad application.
 * Coordinates between the UI components and file management.
 */
public class NotepadFrame extends JFrame implements MenuBarBuilder.MenuActionListener {
    private EditorPanel editorPanel;
    private StatusBar statusBar;
    private FileManager fileManager;
    private JFileChooser fileChooser;

    public NotepadFrame() {
        // Frame initialization
        setTitle("Notepad - Untitled");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));

        // Initialize managers and components
        fileManager = new FileManager();
        editorPanel = new EditorPanel();
        statusBar = new StatusBar();
        fileChooser = new JFileChooser();

        // Setup file chooser
        setupFileChooser();

        // Setup menu bar
        MenuBarBuilder menuBuilder = new MenuBarBuilder(this);
        setJMenuBar(menuBuilder.getMenuBar());

        // Setup layout
        setLayout(new BorderLayout());
        add(editorPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // Setup editor panel modification listener
        editorPanel.setOnModified(() -> {
            if (!fileManager.isModified()) {
                fileManager.setModified(true);
                updateTitle();
            }
        });

        // Setup window close handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        setVisible(true);
        editorPanel.requestTextAreaFocus();
    }

    /**
     * Configures the file chooser dialog.
     */
    private void setupFileChooser() {
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Files (*.java)", "java"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Markdown Files (*.md)", "md"));
    }

    /**
     * Updates the window title with the current file name and modification status.
     */
    private void updateTitle() {
        String title = "Notepad - " + fileManager.getFileName();
        if (fileManager.isModified()) {
            title += " *";
        }
        setTitle(title);
    }

    /**
     * Prompts the user to save if there are unsaved changes.
     *
     * @return true if user wants to continue, false if cancelled
     */
    private boolean promptSaveIfModified() {
        if (fileManager.isModified()) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Current document has unsaved changes. Do you want to save?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                onSaveFile();
            }
            return result != JOptionPane.CANCEL_OPTION;
        }
        return true;
    }

    @Override
    public void onNewFile() {
        if (!promptSaveIfModified()) {
            return;
        }
        editorPanel.clear();
        fileManager.clearCurrentFilePath();
        fileManager.setModified(false);
        updateTitle();
        statusBar.setStatus("New document created");
    }

    @Override
    public void onOpenFile() {
        if (!promptSaveIfModified()) {
            return;
        }

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = fileManager.readFile(file.getAbsolutePath());
                editorPanel.setText(content);
                fileManager.setCurrentFilePath(file.getAbsolutePath());
                fileManager.setModified(false);
                updateTitle();
                statusBar.fileOpened(file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                statusBar.showError("Failed to open file");
            }
        }
    }

    @Override
    public void onSaveFile() {
        if (fileManager.isFileOpen()) {
            try {
                fileManager.writeFile(fileManager.getCurrentFilePath(), editorPanel.getText());
                fileManager.setModified(false);
                updateTitle();
                statusBar.fileSaved(new File(fileManager.getCurrentFilePath()).getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                statusBar.showError("Failed to save file");
            }
        } else {
            onSaveAsFile();
        }
    }

    @Override
    public void onSaveAsFile() {
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                fileManager.writeFile(file.getAbsolutePath(), editorPanel.getText());
                fileManager.setCurrentFilePath(file.getAbsolutePath());
                fileManager.setModified(false);
                updateTitle();
                statusBar.fileSaved(file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                statusBar.showError("Failed to save file");
            }
        }
    }

    @Override
    public void onExit() {
        if (!promptSaveIfModified()) {
            return;
        }
        System.exit(0);
    }

    @Override
    public void onCut() {
        editorPanel.getTextArea().cut();
    }

    @Override
    public void onCopy() {
        editorPanel.getTextArea().copy();
    }

    @Override
    public void onPaste() {
        editorPanel.getTextArea().paste();
    }

    @Override
    public void onSelectAll() {
        editorPanel.getTextArea().selectAll();
    }

    @Override
    public void onToggleWordWrap() {
        boolean enabled = !editorPanel.isLineWrap();
        editorPanel.setLineWrap(enabled);
        statusBar.setStatus(enabled ? "Word wrap: ON" : "Word wrap: OFF");
    }

    @Override
    public void onAbout() {
        JOptionPane.showMessageDialog(
                this,
                "Notepad v2.0\nA modular text editor built with Java Swing\n\n" +
                        "Features:\n" +
                        "- Create, open, and save text files\n" +
                        "- Cut, copy, and paste operations\n" +
                        "- Word wrap toggle\n" +
                        "- Clean, modular architecture",
                "About Notepad",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
