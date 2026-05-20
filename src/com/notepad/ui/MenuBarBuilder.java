package com.notepad.ui;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * MenuBar provides menu structure and actions for the Notepad application.
 * Organizes File, Edit, Format, and Help menus.
 */
public class MenuBarBuilder {
    private JMenuBar menuBar;
    private MenuActionListener actionListener;

    public interface MenuActionListener {
        void onNewFile();
        void onOpenFile();
        void onSaveFile();
        void onSaveAsFile();
        void onExit();
        void onCut();
        void onCopy();
        void onPaste();
        void onSelectAll();
        void onToggleWordWrap();
        void onAbout();
    }

    public MenuBarBuilder(MenuActionListener listener) {
        this.actionListener = listener;
        this.menuBar = new JMenuBar();
        buildMenus();
    }

    /**
     * Builds all menus for the application.
     */
    private void buildMenus() {
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createFormatMenu());
        menuBar.add(createHelpMenu());
    }

    /**
     * Creates the File menu with New, Open, Save, Save As, and Exit options.
     *
     * @return JMenu for File operations
     */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem = new JMenuItem("New", KeyEvent.VK_N);
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newItem.addActionListener(e -> actionListener.onNewFile());
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("Open", KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> actionListener.onOpenFile());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(e -> actionListener.onSaveFile());
        fileMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save As...", KeyEvent.VK_A);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        saveAsItem.addActionListener(e -> actionListener.onSaveAsFile());
        fileMenu.add(saveAsItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitItem.addActionListener(e -> actionListener.onExit());
        fileMenu.add(exitItem);

        return fileMenu;
    }

    /**
     * Creates the Edit menu with Cut, Copy, Paste, and Select All options.
     *
     * @return JMenu for Edit operations
     */
    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem cutItem = new JMenuItem("Cut", KeyEvent.VK_T);
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cutItem.addActionListener(e -> actionListener.onCut());
        editMenu.add(cutItem);

        JMenuItem copyItem = new JMenuItem("Copy", KeyEvent.VK_C);
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        copyItem.addActionListener(e -> actionListener.onCopy());
        editMenu.add(copyItem);

        JMenuItem pasteItem = new JMenuItem("Paste", KeyEvent.VK_P);
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        pasteItem.addActionListener(e -> actionListener.onPaste());
        editMenu.add(pasteItem);

        editMenu.addSeparator();

        JMenuItem selectAllItem = new JMenuItem("Select All", KeyEvent.VK_A);
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        selectAllItem.addActionListener(e -> actionListener.onSelectAll());
        editMenu.add(selectAllItem);

        return editMenu;
    }

    /**
     * Creates the Format menu with Word Wrap option.
     *
     * @return JMenu for Format operations
     */
    private JMenu createFormatMenu() {
        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic(KeyEvent.VK_O);

        JMenuItem wordWrapItem = new JMenuItem("Word Wrap");
        wordWrapItem.addActionListener(e -> actionListener.onToggleWordWrap());
        formatMenu.add(wordWrapItem);

        return formatMenu;
    }

    /**
     * Creates the Help menu with About option.
     *
     * @return JMenu for Help
     */
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> actionListener.onAbout());
        helpMenu.add(aboutItem);

        return helpMenu;
    }

    /**
     * Gets the constructed menu bar.
     *
     * @return JMenuBar instance
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
