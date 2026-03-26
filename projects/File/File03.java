package File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File03 implements FileInterface {
    private String fileName = "filename.txt";

    public File03(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public boolean create() {
        return true;
    }

    @Override
    public void write(String content) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName, false)) {
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String read() {
        String content = null;
        try (FileInputStream fileInputStream = new FileInputStream(fileName)){
            byte[] bytes = fileInputStream.readAllBytes();
            content = new String(bytes, "UTF-8");
            fileInputStream.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    @Override
    public void delete() {
        Path filePath = Paths.get(fileName);
        try {
            Files.delete(filePath);
            System.out.println("File deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
