package DesignPatterns.CreationalPatterns.Builder;

public interface VehicleBuilder<T> {

    public VehicleBuilder<T> setVehicleType(VehicleType vehicleType);
    public VehicleBuilder<T> setSeats(int seats);
    public VehicleBuilder<T> setEngine(Engine engine);
    public VehicleBuilder<T> setTransmission(Transmission transmission);
    public T build();
}
