import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Notepad extends JFrame {
    private JTextArea textArea;
    private JLabel statusBar;
    private String currentFilePath;
    private boolean isModified;

    public Notepad() {
        // Frame setup
        setTitle("Notepad - Untitled");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create menu bar
        createMenuBar();

        // Create text area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isModified) {
                    isModified = true;
                    updateTitle();
                }
            }
        });

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Create status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(statusBar, BorderLayout.SOUTH);

        // Window close handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem = new JMenuItem("New", KeyEvent.VK_N);
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newItem.addActionListener(e -> newFile());
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("Open", KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> openFile());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(e -> saveFile());
        fileMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save As...", KeyEvent.VK_A);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        saveAsItem.addActionListener(e -> saveAsFile());
        fileMenu.add(saveAsItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitItem.addActionListener(e -> exit());
        fileMenu.add(exitItem);

        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem undoItem = new JMenuItem("Undo", KeyEvent.VK_U);
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoItem.addActionListener(e -> textArea.setText(""));
        editMenu.add(undoItem);

        editMenu.addSeparator();

        JMenuItem cutItem = new JMenuItem("Cut", KeyEvent.VK_T);
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cutItem.addActionListener(e -> textArea.cut());
        editMenu.add(cutItem);

        JMenuItem copyItem = new JMenuItem("Copy", KeyEvent.VK_C);
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        copyItem.addActionListener(e -> textArea.copy());
        editMenu.add(copyItem);

        JMenuItem pasteItem = new JMenuItem("Paste", KeyEvent.VK_P);
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        pasteItem.addActionListener(e -> textArea.paste());
        editMenu.add(pasteItem);

        editMenu.addSeparator();

        JMenuItem selectAllItem = new JMenuItem("Select All", KeyEvent.VK_A);
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        selectAllItem.addActionListener(e -> textArea.selectAll());
        editMenu.add(selectAllItem);

        // Format menu
        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic(KeyEvent.VK_O);

        JMenuItem wordWrapItem = new JMenuItem("Word Wrap");
        wordWrapItem.addActionListener(e -> {
            textArea.setLineWrap(!textArea.getLineWrap());
            statusBar.setText(textArea.getLineWrap() ? "Word wrap: ON" : "Word wrap: OFF");
        });
        formatMenu.add(wordWrapItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void newFile() {
        if (isModified) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Current document has unsaved changes. Do you want to save?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        textArea.setText("");
        currentFilePath = null;
        isModified = false;
        updateTitle();
        statusBar.setText("New document created");
    }

    private void openFile() {
        if (isModified) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Current document has unsaved changes. Do you want to save?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("All Files", "*"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                textArea.setText(content);
                currentFilePath = file.getAbsolutePath();
                isModified = false;
                updateTitle();
                statusBar.setText("File opened: " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                statusBar.setText("Error opening file");
            }
        }
    }

    private void saveFile() {
        if (currentFilePath != null) {
            try {
                Files.write(Paths.get(currentFilePath), textArea.getText().getBytes());
                isModified = false;
                updateTitle();
                statusBar.setText("File saved: " + new File(currentFilePath).getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                statusBar.setText("Error saving file");
            }
        } else {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("All Files", "*"));
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Files.write(Paths.get(file.getAbsolutePath()), textArea.getText().getBytes());
                currentFilePath = file.getAbsolutePath();
                isModified = false;
                updateTitle();
                statusBar.setText("File saved as: " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                statusBar.setText("Error saving file");
            }
        }
    }

    private void exit() {
        if (isModified) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Current document has unsaved changes. Do you want to save?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                saveFile();
                System.exit(0);
            } else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(
                this,
                "Notepad v1.0\nA simple text editor built with Java Swing",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateTitle() {
        String title = "Notepad - ";
        if (currentFilePath != null) {
            title += new File(currentFilePath).getName();
        } else {
            title += "Untitled";
        }
        if (isModified) {
            title += " *";
        }
        setTitle(title);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Notepad::new);
    }
}
