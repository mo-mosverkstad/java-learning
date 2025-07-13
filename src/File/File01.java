package File;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class File01 implements FileInterface {
    private String fileName = "filename.txt";
    private File file = null;

    public File01(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    @Override
    public boolean create() {
        if (file.exists()) {
            System.out.println("File exists. Creating new file is ignored.");
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                    System.out.println("File path: " + file.getAbsolutePath());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void write(String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String read() {
        StringBuilder content = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                content.append(data).append("\n");
            }
            content.deleteCharAt(content.length() - 1);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return content.toString();
    }

    @Override
    public void delete() {
        this.file.delete();
    }
}
