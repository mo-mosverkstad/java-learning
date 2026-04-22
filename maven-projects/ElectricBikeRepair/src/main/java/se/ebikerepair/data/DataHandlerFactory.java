package se.ebikerepair.data;

import java.lang.reflect.InvocationTargetException;

public class DataHandlerFactory {

    private DataHandlerFactory() {}
    
    public static DataHandler create(String className) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> classObject = Class.forName(className);
        return (DataHandler) classObject.getConstructor().newInstance();
    }
}
