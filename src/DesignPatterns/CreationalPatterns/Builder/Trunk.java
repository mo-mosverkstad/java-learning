package DesignPatterns.CreationalPatterns.Builder;

public class Trunk {
    private VehicleType vehicleType;
    private int seats;
    private Engine engine;

    public Trunk() {
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Trunk setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public int getSeats() {
        return seats;
    }

    public Trunk setSeats(int seats) {
        this.seats = seats;
        return this;
    }

    public Engine getEngine() {
        return engine;
    }

    public Trunk setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    @Override
    public String toString() {
        return "Trunk{" +
                "vehicleType=" + vehicleType +
                ", seats=" + seats +
                ", engine=" + engine +
                '}';
    }

}
