package DesignPatterns.CreationalPatterns.Builder;

public class TrunkBuilder implements VehicleBuilder<Trunk> {

    private VehicleType vehicleType;
    private int seats;
    private Engine engine;

    public TrunkBuilder setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public TrunkBuilder setSeats(int seats) {
        this.seats = seats;
        return this;
    }

    public TrunkBuilder setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public TrunkBuilder setTransmission(Transmission transmission) {
        return this;
        
    }

    @Override
    public Trunk build() {
        return new Trunk()
            .setVehicleType(vehicleType)
            .setSeats(seats)
            .setEngine(engine);
    }
}
