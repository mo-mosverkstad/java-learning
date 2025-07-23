package DesignPatterns.CreationalPatterns.Builder;

public class CarBuilder implements VehicleBuilder<Car> {

    private VehicleType vehicleType = VehicleType.CITY_CAR;
    private int seats = 5;
    private Engine engine = new Engine(1.6, 100);
    private Transmission transmission = Transmission.MANUAL;

    @Override
    public VehicleBuilder<Car> setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    @Override
    public VehicleBuilder<Car> setSeats(int seats) {
        this.seats = seats;
        return this;
    }

    @Override
    public VehicleBuilder<Car> setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    @Override
    public VehicleBuilder<Car> setTransmission(Transmission transmission) {
        this.transmission = transmission;
        return this;
    }

    @Override
    public Car build() {
        return new Car(vehicleType, seats, engine, transmission);
    }

}
