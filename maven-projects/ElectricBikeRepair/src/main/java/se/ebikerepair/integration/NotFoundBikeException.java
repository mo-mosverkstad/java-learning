package se.ebikerepair.integration;

/**
 * Thrown when no bike is found for the given serial number.
 */
public class NotFoundBikeException extends Exception {
    private final String serialNumber;

    /**
     * Creates an exception indicating no bike was found for the given serial number.
     *
     * @param serialNumber the serial number that was not found
     */
    public NotFoundBikeException(String serialNumber) {
        super("Bike not found");
        this.serialNumber = serialNumber;
    }

    /**
     * Returns the serial number that was not found.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }
}
