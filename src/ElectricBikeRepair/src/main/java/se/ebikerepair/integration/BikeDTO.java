package se.ebikerepair.integration;

public class BikeDTO {
    private final String brand;
    private final String model;
    private final String serialNumber;

    public BikeDTO(String brand, String model, String serialNumber) {
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public String getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    
    @Override
    public String toString() {
        return "Bike Information: {" +
                "brand: '" + brand + '\'' +
                ", model: '" + model + '\'' +
                ", serialNumber: '" + serialNumber + '\'' +
                '}';
    }
}
