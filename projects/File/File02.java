package File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class File02 implements FileInterface {
    private Path path = null;

    public File02(String fileName) {
        this.path = Path.of(fileName);
    }
    
    @Override
    public boolean create() {
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
            return false;
        }
        return true;
    }

    @Override
    public void write(String content) {
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String read() {
        String content = null;
        try {
            content = String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void delete() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
