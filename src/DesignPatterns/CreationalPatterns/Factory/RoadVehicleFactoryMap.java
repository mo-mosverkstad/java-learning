package DesignPatterns.CreationalPatterns.Factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RoadVehicleFactoryMap {
    private static final String CAR_KEYWORD = "Car";
    private static final String TRUCK_KEYWORD = "Truck";
    private static final Map<String, Supplier<RoadVehicle>> VEHICLE_MAP = new HashMap<>(){{
        put(CAR_KEYWORD, Car::new);
        put(TRUCK_KEYWORD, Truck::new);
    }};

    public static RoadVehicle create(String type) {
        if (VEHICLE_MAP.containsKey(type)) {
            Supplier<RoadVehicle> supplier = VEHICLE_MAP.get(type);
            return supplier.get();
        }
        return null;
    }
}
