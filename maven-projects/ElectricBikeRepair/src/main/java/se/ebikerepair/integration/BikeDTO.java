package se.ebikerepair.integration;

import se.ebikerepair.constant.PrintoutFormat;

/**
 * Immutable data transfer object representing an electric bike.
 *
 * @param brand the bike brand
 * @param model the bike model
 * @param serialNumber the bike serial number
 */
public record BikeDTO(String brand, String model, String serialNumber) {

    @Override
    public String toString() {
        return String.format(PrintoutFormat.BIKE_PRINTOUT_FORMAT, brand, model, serialNumber);
    }
}
