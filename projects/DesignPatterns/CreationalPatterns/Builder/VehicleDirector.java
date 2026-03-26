package DesignPatterns.CreationalPatterns.Builder;

public class VehicleDirector <T>{

    @SuppressWarnings("unchecked")
    public T build(String vehicle) {

        if (vehicle.equals("minicar")) {
            return (T) new CarBuilder().setVehicleType(VehicleType.CITY_CAR)
                .setSeats(5)
                .setEngine(new Engine(1.6, 100))
                .setTransmission(Transmission.MANUAL)
                .build();
        } else if (vehicle.equals("sportsCar")) {
            return (T) new CarBuilder().setVehicleType(VehicleType.SPORTS_CAR)
                .setSeats(2)
                .setEngine(new Engine(3.0, 150))
                .setTransmission(Transmission.AUTOMATIC)
                .build();
        } else if (vehicle.equals("Toyota Motors")) {
            return (T) new TrunkBuilder().setVehicleType(VehicleType.TRUCK)
                .setSeats(2)
                .setEngine(new Engine(5.8, 200))
                .build();
        }
        return null;
    }
}
