package Interface;
import java.lang.reflect.InvocationTargetException;

public class ComputerFactory {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ComputerInterface createComputer(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class c= Class.forName(name);
        return (ComputerInterface) c.getConstructor().newInstance();
    }
}
