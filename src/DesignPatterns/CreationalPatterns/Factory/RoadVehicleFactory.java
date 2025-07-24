package DesignPatterns.CreationalPatterns.Factory;

import java.lang.reflect.InvocationTargetException;

public class RoadVehicleFactory {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static RoadVehicle create(String type) {
        try {
            Class c = Class.forName(type);
            return (RoadVehicle) c.getConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
