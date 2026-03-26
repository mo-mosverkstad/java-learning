package se.ebikerepair.integration;

import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;

public class CustomerDTO {
    private final String name;
    private final String telephoneNumber;
    private final String emailAddress;
    private final List<BikeDTO> bikes;

    public CustomerDTO(String name, String telephoneNumber, String emailAddress, List<BikeDTO> bikes) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.bikes = bikes;
    }

    public String getName() {
        return name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<BikeDTO> getBikes() {
        return bikes;
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
