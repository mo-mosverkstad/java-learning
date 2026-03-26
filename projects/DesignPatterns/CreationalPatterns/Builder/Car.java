package DesignPatterns.CreationalPatterns.Builder;

public class Car {
    private final VehicleType vehicleType;
    private final int seats;
    private final Engine engine;
    private final Transmission transmission;

    public Car(VehicleType vehicleType, int seats, Engine engine, Transmission transmission) {
        this.vehicleType = vehicleType;
        this.seats = seats;
        this.engine = engine;
        this.transmission = transmission;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getSeats() {
        return seats;
    }

    public Engine getEngine() {
        return engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vehicleType=" + vehicleType +
                ", seats=" + seats +
                ", engine=" + engine +
                ", transmission=" + transmission +
                '}';
    }
}
