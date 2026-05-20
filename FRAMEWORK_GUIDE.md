# Java Notepad - Professional GUI Framework Edition

A production-grade text editor built with **Java Swing** - the industry-standard GUI framework for Java desktop applications.

## Framework Architecture

### GUI Framework: Java Swing
**Swing** is a mature, professional-grade GUI toolkit that:
- ✅ Ships with all JDK installations (no external dependencies)
- ✅ Provides native look & feel on Windows, Mac, and Linux
- ✅ Offers comprehensive component library
- ✅ Includes professional event handling and MVC patterns
- ✅ Widely used in enterprise applications

### Application Architecture

```
jnotes/
├── src/com/notepad/
│   ├── NotepadApplication.java          # Entry point - manages EDT
│   ├── ui/
│   │   ├── NotepadFrame.java            # Main window (View + Controller)
│   │   ├── MenuBarBuilder.java          # Menu construction
│   │   └── StatusBar.java               # Status display component
│   ├── editor/
│   │   └── EditorPanel.java             # Text editing component (View)
│   ├── file/
│   │   └── FileManager.java             # File I/O & state (Model)
│   ├── model/
│   │   └── Document.java                # Document state model (Model)
│   └── util/
│       └── FileService.java             # File utility service
└── out/classes/                          # Compiled bytecode
```

## Design Patterns & Best Practices

### 1. MVC (Model-View-Controller) Pattern
- **Model**: `FileManager`, `Document` - manage data and state
- **View**: `NotepadFrame`, `EditorPanel`, `StatusBar`, `MenuBarBuilder` - UI components
- **Controller**: `NotepadFrame` - handles user interactions and business logic

### 2. Separation of Concerns
```
FileManager    ← Pure file I/O operations
Document       ← Document state management
EditorPanel    ← Text editing UI component
StatusBar      ← Status feedback display
MenuBarBuilder ← Menu construction (Builder pattern)
NotepadFrame   ← Orchestrates all components
```

### 3. Key Design Patterns Used
- **Builder Pattern**: `MenuBarBuilder` - constructs complex menus
- **Observer Pattern**: Swing components use listeners for events
- **Singleton Pattern**: Single document instance per application
- **Facade Pattern**: `NotepadFrame` presents unified interface
- **EDT (Event Dispatch Thread)**: Safe GUI updates from `NotepadApplication`

### 4. Swing Component Hierarchy
```
JFrame (NotepadFrame)
├── JMenuBar (MenuBarBuilder)
├── JPanel (EditorPanel)
│   └── JTextArea
│       └── JScrollPane
└── JLabel (StatusBar)
```

## Features

### File Operations
- **New**: Create fresh document with unsaved changes detection
- **Open**: Load any text file with FileChooser
- **Save**: Save to current file or prompt for path
- **Save As**: Save with new filename/location
- **Exit**: Clean shutdown with save prompts

### Edit Operations
- **Cut/Copy/Paste**: Full clipboard integration via JTextArea
- **Select All**: Select entire document
- **Text Navigation**: Standard keyboard shortcuts

### Formatting & Display
- **Word Wrap**: Toggle line wrapping mode
- **Font**: 14pt Arial monospace font
- **Line Numbers**: Terminal-style display
- **Status Bar**: Real-time feedback and file info
- **Modified Indicator**: Asterisk (*) in title when unsaved

### User Experience
- **Unsaved Changes Warning**: Dialog prompts before losing work
- **File Info Display**: Shows current file name in title bar
- **Status Messages**: Detailed feedback for all operations
- **Keyboard Shortcuts**: Fast access to common functions
- **Professional Look & Feel**: Native OS appearance

## Swing vs Other Frameworks

| Aspect | Swing | JavaFX | SWT |
|--------|-------|--------|-----|
| **Part of JDK** | ✅ Yes | ❌ Separate | ❌ Separate |
| **Learning Curve** | Moderate | Steep | Moderate |
| **Modern Features** | Good | Excellent | Good |
| **Enterprise Use** | Very common | Growing | Established |
| **Desktop Apps** | Perfect | Perfect | Good |
| **Dependencies** | None | Requires download | Requires download |

**Why Swing for this project:**
- Built into Java - works everywhere
- Mature and stable
- Excellent for text editor GUI
- No external dependencies
- Industry standard choice

## Building the Application

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Linux, macOS, or Windows

### Build Process

**Using build script (Recommended):**
```bash
./build.sh          # Unix/Linux/macOS
build.bat           # Windows
```

**Manual build:**
```bash
mkdir -p out/classes
javac -d out/classes -sourcepath src src/com/notepad/NotepadApplication.java
```

## Running the Application

**Using run script (Recommended):**
```bash
./run.sh            # Unix/Linux/macOS
run.bat             # Windows
```

**Manual execution:**
```bash
java -cp out/classes com.notepad.NotepadApplication
```

## Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| Ctrl+N | New document |
| Ctrl+O | Open file |
| Ctrl+S | Save file |
| Ctrl+Shift+S | Save As |
| Ctrl+X | Cut |
| Ctrl+C | Copy |
| Ctrl+V | Paste |
| Ctrl+A | Select All |

## Code Organization by Responsibility

### `ui/NotepadFrame.java`
- Main application window
- Orchestrates all UI components
- Implements menu action listener
- Handles application logic flow
- Manages file operations coordination

### `ui/MenuBarBuilder.java`
- Menu construction (Builder pattern)
- Keyboard shortcut setup
- Menu action delegation via listener interface
- Keeps menu logic separate from window logic

### `ui/StatusBar.java`
- Status message display
- Helper methods for common messages
- JLabel extension with specialized methods

### `editor/EditorPanel.java`
- Text area management
- Line wrapping control
- Modification detection
- Callback support for text changes

### `file/FileManager.java`
- File path tracking
- Modified state management
- File name extraction
- High-level file state queries

### `util/FileService.java`
- Pure file I/O operations
- No state management
- Reusable utility methods
- Static methods for convenience

### `model/Document.java`
- Document data representation
- Binds to text changes for modification tracking
- Provides clean separation between UI and data

## Swing Component Explanation

### JFrame
- Top-level window container
- Provides window decorations (title bar, buttons)
- Manages window events

### JMenuBar
- Contains menus and menu items
- Handles accelerators (keyboard shortcuts)
- Attached to JFrame

### JTextArea
- Multi-line text component
- Supports word wrap
- Provides text selection and clipboard support
- Integrated with JScrollPane for scrolling

### JScrollPane
- Wraps JTextArea to add scrollbars
- Vertical and horizontal scrolling
- Smooth scrolling support

### JLabel
- Text display component
- No text editing capability
- Perfect for status messages

## Event Handling in Swing

Swing uses an **event-driven model**:

```java
// Action listeners for buttons/menu items
menuItem.addActionListener(e -> onFileOpen());

// Text listeners for text changes
textArea.getDocument().addDocumentListener(
    new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { /* ... */ }
        public void removeUpdate(DocumentEvent e) { /* ... */ }
        public void changedUpdate(DocumentEvent e) { /* ... */ }
    }
);

// Window listeners for close events
addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent e) { exit(); }
});
```

## Thread Safety in Swing

**Important**: All GUI operations must happen on the Event Dispatch Thread (EDT):

```java
// Correct way to update GUI from background thread
SwingUtilities.invokeLater(() -> {
    textArea.setText("Updated text");
});
```

The application ensures this in `NotepadApplication.main()`:
```java
SwingUtilities.invokeLater(() -> {
    new NotepadFrame();  // GUI construction on EDT
});
```

## Extending the Application

### Adding a New Menu Item
1. Add method to `MenuBarBuilder.MenuActionListener` interface
2. Implement method in `NotepadFrame`
3. Add menu item creation in `MenuBarBuilder.createFileMenu()`

### Adding Text Formatting
1. Add UI controls to `EditorPanel`
2. Create corresponding methods in `EditorPanel`
3. Call from `NotepadFrame` event handlers

### Adding Search Functionality
1. Create `SearchService` utility class
2. Create `SearchDialog` component
3. Integrate with `NotepadFrame` event handling

## Performance Considerations

### Text Area Performance
- **Large files**: JTextArea performs well up to ~1MB
- **Beyond that**: Consider using specialized text components
- **Line wrapping**: Disable for very large files

### Memory Usage
- **Typical document**: < 1MB in memory
- **File I/O**: Uses streaming for large files
- **UI components**: Minimal overhead

## Troubleshooting

### Application Won't Start
```bash
# Check Java installation
java -version

# Verify compilation
ls out/classes/com/notepad/*.class
```

### GUI Not Appearing
- Check if running headless system (SSH without display)
- Verify DISPLAY environment variable on Linux
- Try setting look and feel explicitly

### Text Not Displaying
- Verify font availability: `java -cp out/classes com.notepad.NotepadApplication`
- Check JTextArea properties in `EditorPanel.java`

## Swing vs JavaFX

This application uses **Swing** because:
- ✅ No external dependencies needed
- ✅ Works on all systems with Java
- ✅ Perfect for text editor use case
- ✅ Battle-tested in production

**JavaFX** would require:
- Download and setup of JavaFX SDK
- Module path configuration
- Additional complexity for this use case

## Future Enhancements

- **Find & Replace**: Add find dialog with regex support
- **Line Numbers**: JPanel with line number rendering
- **Syntax Highlighting**: DocumentListener with styled document
- **Tab Support**: Use JTabbedPane for multiple documents
- **Themes**: Multiple color schemes via UIManager
- **Recent Files**: Menu with last opened files
- **Print Support**: PrinterJob integration
- **Undo/Redo**: UndoManager integration

## References

- [Official Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Swing API Documentation](https://docs.oracle.com/javase/11/docs/api/java.desktop/javax/swing/package-summary.html)
- [MVC Pattern in Swing](https://docs.oracle.com/javase/tutorial/uiswing/architecture/model.html)
- [Event Handling in Swing](https://docs.oracle.com/javase/tutorial/uiswing/events/)

## License

Educational project. Feel free to modify and extend as needed.

---

**Built with**: Java Swing GUI Framework
**Architecture**: MVC with Separation of Concerns
**Status**: Production-Ready
