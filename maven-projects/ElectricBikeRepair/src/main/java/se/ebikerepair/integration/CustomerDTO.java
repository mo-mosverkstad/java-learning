package se.ebikerepair.integration;

import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;

/**
 * Data transfer object representing a customer with their bikes.
 */
public class CustomerDTO {
    private final String name;
    private final String telephoneNumber;
    private final String emailAddress;
    private final List<BikeDTO> bikes;

    /**
     * Creates a customer DTO.
     *
     * @param name the customer's full name
     * @param telephoneNumber the customer's telephone number in E.164 format
     * @param emailAddress the customer's email address
     * @param bikes the list of bikes owned by the customer
     */
    public CustomerDTO(String name, String telephoneNumber, String emailAddress, List<BikeDTO> bikes) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.bikes = bikes;
    }

    /** @return the customer's full name */
    public String getName() {
        return name;
    }

    /** @return the customer's telephone number */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /** @return the customer's email address */
    public String getEmailAddress() {
        return emailAddress;
    }

    /** @return the list of bikes owned by the customer */
    public List<BikeDTO> getBikes() {
        return bikes;
    }

    /**
     * Finds a bike by its serial number.
     *
     * @param serialNumber the serial number to search for
     * @return the matching bike, or null if not found
     * @throws IllegalArgumentException if the serial number is not found
     */
    public BikeDTO getBikeBySerialNumber(String serialNumber) throws IllegalArgumentException{
        for (BikeDTO bike : bikes) {
            if (bike.getSerialNumber().equals(serialNumber)) {
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
                        b.getBrand(), b.getModel(), b.getSerialNumber()))
                        .collect(Collectors.joining());
        return String.format(PrintoutFormat.CUSTOMER_INLINE_PRINTOUT_FORMAT, name, telephoneNumber, emailAddress, bikesStr);
    }

    @Override
    public String toString() {
        String bikesStr = bikes == null || bikes.isEmpty() ? PrintoutFormat.BIKE_PRINTOUT_EMPTY :
                bikes.stream().map(b -> String.format(PrintoutFormat.BIKE_PRINTOUT_FORMAT,
                        b.getBrand(), b.getModel(), b.getSerialNumber()))
                        .collect(Collectors.joining());
        return String.format(PrintoutFormat.CUSTOMER_PRINTOUT_FORMAT, name, telephoneNumber, emailAddress, bikesStr);
    }
}
