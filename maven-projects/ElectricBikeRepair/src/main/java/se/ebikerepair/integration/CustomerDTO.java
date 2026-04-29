package se.ebikerepair.integration;

import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;

/**
 * Immutable data transfer object representing a customer with their bikes.
 *
 * @param name the customer's full name
 * @param telephoneNumber the customer's telephone number in E.164 format
 * @param emailAddress the customer's email address
 * @param bikes the list of bikes owned by the customer
 */
public record CustomerDTO(String name, String telephoneNumber, String emailAddress, MembershipDTO membership, List<BikeDTO> bikes) {

    /**
     * Finds a bike by its serial number.
     *
     * @param serialNumber the serial number to search for
     * @return the matching bike
     * @throws IllegalArgumentException if the serial number is not found
     */
    public BikeDTO getBikeBySerialNumber(String serialNumber) throws IllegalArgumentException {
        for (BikeDTO bike : bikes) {
            if (bike.serialNumber().equals(serialNumber)) {
                return bike;
            }
        }
        throw new IllegalArgumentException(String.format("No bike found with serial number %s", serialNumber));
    }

    /**
     * Returns an inline formatted string for use inside repair order printouts (without borders).
     *
     * @return the inline formatted customer string
     */
    public String toInlineString() {
        String bikesStr = bikes == null || bikes.isEmpty() ? PrintoutFormat.BIKE_PRINTOUT_EMPTY :
                bikes.stream().map(b -> String.format(PrintoutFormat.BIKE_PRINTOUT_FORMAT,
                        b.brand(), b.model(), b.serialNumber()))
                        .collect(Collectors.joining());
        return String.format(PrintoutFormat.CUSTOMER_INLINE_PRINTOUT_FORMAT, name, telephoneNumber, emailAddress, bikesStr);
    }

    @Override
    public String toString() {
        String bikesStr = bikes == null || bikes.isEmpty() ? PrintoutFormat.BIKE_PRINTOUT_EMPTY :
                bikes.stream().map(b -> String.format(PrintoutFormat.BIKE_PRINTOUT_FORMAT,
                        b.brand(), b.model(), b.serialNumber()))
                        .collect(Collectors.joining());
        return String.format(PrintoutFormat.CUSTOMER_PRINTOUT_FORMAT, name, telephoneNumber, emailAddress, bikesStr);
    }
}
