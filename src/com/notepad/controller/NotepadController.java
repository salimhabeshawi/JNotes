package com.notepad.controller;

import com.notepad.model.Document;
import com.notepad.util.FileService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * NotepadController handles all user interactions and application logic.
 * Implements the MVC controller pattern.
 */
public class NotepadController {
    @FXML
    private TextArea textArea;
    @FXML
    private Label statusLabel;
    @FXML
    private Label fileNameLabel;

    private Document document;
    private Stage stage;
    private FileChooser fileChooser;

    /**
     * Initializes the controller.
     * Called automatically after FXML loading.
     */
    @FXML
    public void initialize() {
        // Initialize document model
        document = new Document();

        // Bind text area to document content
        textArea.textProperty().bindBidirectional(document.contentProperty());

        // Update UI when document properties change
        document.modifiedProperty().addListener((obs, oldVal, newVal) -> updateWindowTitle());
        document.fileNameProperty().addListener((obs, oldVal, newVal) -> {
            fileNameLabel.setText(document.getFileName());
            updateWindowTitle();
        });

        // Setup file chooser
        setupFileChooser();

        // Initial status update
        updateStatus("Ready");
    }

    /**
     * Sets up the file chooser dialog.
     */
    private void setupFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Java Files", "*.java"),
                new FileChooser.ExtensionFilter("Markdown Files", "*.md"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    /**
     * Sets the stage reference.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        updateWindowTitle();
    }

    /**
     * Creates a new document after prompting to save if needed.
     */
    @FXML
    public void newFile() {
        if (document.isModified() && !promptSave()) {
            return;
        }
        document.reset();
        updateStatus("New document created");
    }

    /**
     * Opens a file dialog to load a document.
     */
    @FXML
    public void openFile() {
        if (document.isModified() && !promptSave()) {
            return;
        }

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                String content = FileService.readFile(selectedFile.getAbsolutePath());
                document.setContent(content);
                document.setFilePath(selectedFile.getAbsolutePath());
                document.setFileName(FileService.getFileName(selectedFile.getAbsolutePath()));
                document.markSaved();
                updateStatus("File opened: " + document.getFileName());
            } catch (IOException e) {
                showError("Error opening file", e.getMessage());
                updateStatus("Error opening file");
            }
        }
    }

    /**
     * Saves the current document.
     */
    @FXML
    public void saveFile() {
        if (document.isFileOpen()) {
            try {
                FileService.writeFile(document.getFilePath(), document.getContent());
                document.markSaved();
                updateStatus("File saved: " + document.getFileName());
            } catch (IOException e) {
                showError("Error saving file", e.getMessage());
                updateStatus("Error saving file");
            }
        } else {
            saveAsFile();
        }
    }

    /**
     * Opens save-as dialog to save with a new name.
     */
    @FXML
    public void saveAsFile() {
        fileChooser.setTitle("Save File");
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            try {
                FileService.writeFile(selectedFile.getAbsolutePath(), document.getContent());
                document.setFilePath(selectedFile.getAbsolutePath());
                document.setFileName(FileService.getFileName(selectedFile.getAbsolutePath()));
                document.markSaved();
                updateStatus("File saved as: " + document.getFileName());
            } catch (IOException e) {
                showError("Error saving file", e.getMessage());
                updateStatus("Error saving file");
            }
        }
        fileChooser.setTitle("Open File");
    }

    /**
     * Toggles word wrap mode.
     */
    @FXML
    public void toggleWordWrap() {
        textArea.setWrapText(!textArea.isWrapText());
        String status = textArea.isWrapText() ? "Word wrap: ON" : "Word wrap: OFF";
        updateStatus(status);
    }

    /**
     * Exits the application.
     */
    @FXML
    public void exitApplication() {
        if (document.isModified() && !promptSave()) {
            return;
        }
        Platform.exit();
    }

    /**
     * Selects all text in the editor.
     */
    @FXML
    public void selectAll() {
        textArea.selectAll();
    }

    /**
     * Shows the About dialog.
     */
    @FXML
    public void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Notepad");
        alert.setHeaderText("Notepad v2.0 - JavaFX Edition");
        alert.setContentText(
                "A modern, modular text editor built with JavaFX\n\n" +
                        "Features:\n" +
                        "• Create, open, and save text files\n" +
                        "• Word wrap toggle\n" +
                        "• Clean, professional GUI\n" +
                        "• MVC architecture with data binding"
        );
        alert.showAndWait();
    }

    /**
     * Prompts user to save if document is modified.
     *
     * @return true if user wants to continue, false if cancelled
     */
    private boolean promptSave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("Save changes?");
        alert.setContentText("The document has unsaved changes. Do you want to save?");

        var result = alert.showAndWait();
        if (result.isPresent() && result.get().getText().equals("OK")) {
            saveFile();
            return true;
        }
        return result.isPresent() && result.get().getText().equals("Cancel");
    }

    /**
     * Updates the window title with file name and modification indicator.
     */
    private void updateWindowTitle() {
        if (stage != null) {
            String title = "Notepad - " + document.getFileName();
            if (document.isModified()) {
                title += " *";
            }
            stage.setTitle(title);
        }
    }

    /**
     * Updates the status bar message.
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Shows an error alert.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
