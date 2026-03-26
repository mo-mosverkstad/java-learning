package DesignPatterns.CreationalPatterns.Factory;

public class DriverMain {

    public static void main(String[] args) {
        // selectVehicleToCreate("DesignPatterns.CreationalPatterns.Factory.Truck").driveOnRoad();
        selectVehicleToCreate("Truck").driveOnRoad();
    }

    private static RoadVehicle selectVehicleToCreate(String vehicleType) {
        return RoadVehicleFactoryMap.create(vehicleType);
    }
}
