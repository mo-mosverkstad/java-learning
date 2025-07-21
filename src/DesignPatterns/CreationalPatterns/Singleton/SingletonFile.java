package DesignPatterns.CreationalPatterns.Singleton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class SingletonFile {

    private static final SingletonFile instance = new SingletonFile();
    private static final String FILE_NAME = "temp.txt";

    private SingletonFile() {
        Path path = Paths.get(FILE_NAME);
        try {
            if (Files.exists(path)) {
                System.out.println("File exists. Creating new file is ignored.");
            } else {
                Files.createFile(path);
                System.out.println("File created: " + path.getFileName());
                System.out.println("File path: " + path.toAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SingletonFile getInstance() {
        return instance;
    }

    public void write(String content) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, false)) {
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
