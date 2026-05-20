package com.notepad.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service for file I/O operations.
 * Handles reading and writing files.
 */
public class FileService {

    /**
     * Reads the content of a file.
     *
     * @param filePath Path to the file
     * @return File content as string
     * @throws IOException If file cannot be read
     */
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Writes content to a file.
     *
     * @param filePath Path to the file
     * @param content  Content to write
     * @throws IOException If file cannot be written
     */
    public static void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }

    /**
     * Gets the file name from a full path.
     *
     * @param filePath Full file path
     * @return File name
     */
    public static String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    /**
     * Checks if a file exists.
     *
     * @param filePath Path to check
     * @return true if file exists
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
