package File;
import java.lang.reflect.InvocationTargetException;

public class FileMain {

    private static final String CONTENT = "hello, world!";

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        FileInterface file = generateFileObject("File01", "C:\\Users\\EWANBIN\\Downloads\\filename.txt");
        fileHandle(file);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static FileInterface generateFileObject(String className, String fileName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class c= Class.forName(className);
        return (FileInterface) c.getConstructor(String.class).newInstance(fileName);
    }
    
    public static void fileHandle(FileInterface fileInterface) {
        if (fileInterface.create()) {
            fileInterface.write(CONTENT);
            String content = fileInterface.read();
            System.out.println(content);
            System.out.println(String.format("The content is right? %b", CONTENT.equals(content)));
            fileInterface.delete();
        }
    }
}
