package se.ebikerepair.integration;

import se.ebikerepair.constant.PrintoutFormat;

/**
 * Data transfer object representing an electric bike.
 */
public class BikeDTO {
    private final String brand;
    private final String model;
    private final String serialNumber;

    /**
     * Creates a bike DTO.
     *
     * @param brand the bike brand
     * @param model the bike model
     * @param serialNumber the bike serial number
     */
    public BikeDTO(String brand, String model, String serialNumber) {
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
    }
    
    /**
     * @return the bike brand
     */
    public String getBrand() {
        return brand;
    }
    
    /**
     * @return the bike model
     */
    public String getModel() {
        return model;
    }

    /**
     * @return the bike serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    
    @Override
    public String toString() {
        return String.format(PrintoutFormat.BIKE_PRINTOUT_FORMAT, brand, model, serialNumber);
    }
}
