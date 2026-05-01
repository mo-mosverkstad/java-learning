package se.ebikerepair.data;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory for creating {@link DataHandler} instances by class name using reflection.
 */
public class DataHandlerFactory {

    private DataHandlerFactory() {}

    /**
     * Creates a DataHandler instance from the given fully qualified class name.
     *
     * @param className the fully qualified class name of the DataHandler implementation
     * @return a new DataHandler instance
     * @throws ClassNotFoundException if the class cannot be found
     * @throws NoSuchMethodException if the class has no no-arg constructor
     * @throws InstantiationException if the class cannot be instantiated
     * @throws IllegalAccessException if the constructor is not accessible
     * @throws InvocationTargetException if the constructor throws an exception
     */
    public static DataHandler create(String className) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> classObject = Class.forName(className);
        return (DataHandler) classObject.getConstructor().newInstance();
    }
}
